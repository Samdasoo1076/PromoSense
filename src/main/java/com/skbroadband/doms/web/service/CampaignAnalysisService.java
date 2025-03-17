package com.skbroadband.doms.web.service;

import com.skbroadband.doms.global.utils.CommCodeUtils;
import com.skbroadband.doms.web.dto.*;
import com.skbroadband.doms.web.entity.Campaign;
import com.skbroadband.doms.web.entity.CampaignMessageMulti;
import com.skbroadband.doms.web.mapper.*;
import com.skbroadband.doms.web.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignAnalysisService {
    private final CampaignLogRepository campaignLogRepository;
    private final CampaignMessageMapper campaignMessageMapper;
    private final CampaignMessageRepository campaignMessageRepository;

    private final CampaignMapper campaignMapper;
    private final CampaignExposureTimeMapper campaignExposureTimeMapper;
    private final CampaignExposureUrlMapper campaignExposureUrlMapper;
    private final CampaignRevisitUrlMapper campaignRevisitUrlMapper;
    private final CampaignSpecUrlMapper campaignSpecUrlMapper;
    private final CampaignTargetUrlMapper campaignTargetUrlMapper;

    private final CampaignAnalysisSupportRepository campaignAnalysisSupportRepository;
    private final CampaignRepository campaignRepository;
    private final CampaignExposureTimeRepository campaignExposureTimeRepository;
    private final CampaignExposureUrlRepository campaignExposureUrlRepository;
    private final CampaignRevisitUrlRepository campaignRevisitUrlRepository;
    private final CampaignSpecUrlRepository campaignSpecUrlRepository;
    private final CampaignTargetUrlRepository campaignTargetUrlRepository;

    private final CommCodeUtils commCodeUtils;
    private final CampaignMessageMultiRepository campaignMessageMultiRepository;

    @Transactional(value = "webTransactionManager")
    public Page<CampaignDto> getCampaignAnalysisList(String keyword, String startDate, String endDate, String gubun, Pageable pageable) {

        Page<CampaignDto> campaignDtos = campaignAnalysisSupportRepository.findByAll(keyword, startDate, endDate, gubun, pageable);
//        campaignDtos.forEach(caDto -> {
//            if(!keyword.equals("")) {
//                caDto.setCaName(
//                        caDto.getCaName().replaceAll(keyword, "<mark class=\"em accent-01\">" + keyword + "</mark>")
//                );
//            }
//        });

        return campaignDtos;
//        return campaignAnalysisSupportRepository.findByAll(keyword, startDate, endDate, gubun, pageable);
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public CampaignDto getCampaignAnalysisDetail(Long id) {

        CampaignDto campaignDto = campaignRepository.findById(id).map(campaignMapper::toDto).orElse(null);
        log.info(campaignDto.toString());
        CampaignMessageDto campaignMessageDto = campaignMessageMapper.toDto(campaignMessageRepository.findByCaNoAndUseTfAndDelTf(Campaign.builder().id(id).build(),
                "Y", "N"));

        if(campaignDto != null) {
            Instant now = Instant.now();
            if(now.isBefore(campaignDto.getCaStartDate())){
                campaignDto.setCaStateNm("s0001");
            }else if(now.isAfter(campaignDto.getCaStartDate()) && now.isBefore(campaignDto.getCaEndDate())){
                if("N".equals(campaignDto.getUseTf())){
                    campaignDto.setCaStateNm("s0003");
                }else{
                    campaignDto.setCaStateNm("s0002");
                }
            }else if(now.isAfter(campaignDto.getCaEndDate())){
                campaignDto.setCaStateNm("s0004");
            }else{
                campaignDto.setCaStateNm("s9999");
            }

            /*if("N".equals(campaignDto.getUseTf())) {
                campaignDto.setCaStateNm("s0004");
            } else {
                if(Instant.now().isBefore(campaignDto.getCaStartDate())) {
                    campaignDto.setCaStateNm("s0001");
                } else if(Instant.now().isAfter(campaignDto.getCaEndDate())) {
                    campaignDto.setCaStateNm("s0003");
                } else {
                    campaignDto.setCaStateNm("s0002");
                }
            }*/
        }

        List<CampaignExposureTimeDto> campaignExposureTimeDtos =
                campaignExposureTimeRepository.findByCaNoOrderByStartTime(Campaign.builder().id(id).build()).stream().map(campaignExposureTimeMapper::toDtoNoLazy).collect(Collectors.toList());
        List<CampaignExposureUrlDto> campaignExposureUrlDtos =
                campaignExposureUrlRepository.findByCaNo(Campaign.builder().id(id).build()).stream().map(campaignExposureUrlMapper::toDtoNoLazy).collect(Collectors.toList());
        List<CampaignRevisitUrlDto> campaignRevisitUrlDtos =
                campaignRevisitUrlRepository.findByCaNo(Campaign.builder().id(id).build()).stream().map(campaignRevisitUrlMapper::toDtoNoLazy).collect(Collectors.toList());
        List<CampaignSpecUrlDto> campaignSpecUrlDtos =
                campaignSpecUrlRepository.findByCaNo(Campaign.builder().id(id).build()).stream().map(campaignSpecUrlMapper::toDtoNoLazy).collect(Collectors.toList());
        List<CampaignTargetUrlDto> campaignTargetUrlDtos =
                campaignTargetUrlRepository.findByCaNo(Campaign.builder().id(id).build()).stream().map(campaignTargetUrlMapper::toDtoNoLazy).collect(Collectors.toList());

        List<CodeDetailDto> codeDetailDtos = commCodeUtils.getCodeList("TIME_SUB");
        Collections.sort(campaignExposureUrlDtos, Collections.reverseOrder());
        Collections.sort(campaignSpecUrlDtos, Collections.reverseOrder());

        List<String> tmpTimeDtos = new ArrayList<String>();
        String tmpEndTime = "";
        String tmpStartTimeNm = "";
        String tmpEndTimeNm = "";
        int tmpCnt = 0;
        for(CampaignExposureTimeDto campaignExposureTimeDto : campaignExposureTimeDtos) {
            tmpCnt++;
            for(CodeDetailDto codeDetailDto : codeDetailDtos) {
                if(codeDetailDto.getDcodeExtValue1().equals(campaignExposureTimeDto.getStartTime())) {
                    if(!tmpEndTime.equals(campaignExposureTimeDto.getStartTime())) {
                        if(!"".equals(tmpEndTime)) {
                            tmpTimeDtos.add(tmpStartTimeNm + tmpEndTimeNm);
                        }
                        tmpStartTimeNm = codeDetailDto.getDcodeNm().split("-")[0].trim();
                        tmpEndTimeNm = " - " + codeDetailDto.getDcodeNm().split("-")[1].trim();
                    } else {
                        tmpEndTimeNm = " - " + codeDetailDto.getDcodeNm().split("-")[1].trim();
                    }

                    tmpEndTime = campaignExposureTimeDto.getEndTime();

                    if(campaignExposureTimeDtos.size() == tmpCnt) {
                        tmpTimeDtos.add(tmpStartTimeNm + tmpEndTimeNm);
                    }
                }
            }
        }

        Float totalAvg = campaignLogRepository.getTotalAvg(id);
        Float moAvg = campaignLogRepository.getAvg(id, "M");
        Float pcAvg = campaignLogRepository.getAvg(id, "P");

        campaignDto.setTotalExpAvg(Float.parseFloat(String.format("%.2f", (totalAvg == null)? 0 : totalAvg)));
        campaignDto.setMoExpAvg(Float.parseFloat(String.format("%.2f", (moAvg == null)? 0 : moAvg)));
        campaignDto.setPcExpAvg(Float.parseFloat(String.format("%.2f", (pcAvg == null)? 0 : pcAvg)));
        campaignDto.setCaTimes(tmpTimeDtos);
        campaignDto.setCampaignMessageDto(campaignMessageDto);
        campaignDto.setCampaignExposureTimeDtos(campaignExposureTimeDtos);
        campaignDto.setCampaignExposureUrlDtos(campaignExposureUrlDtos);
        campaignDto.setCampaignRevisitUrlDtos(campaignRevisitUrlDtos);
        campaignDto.setCampaignSpecUrlDtos(campaignSpecUrlDtos);
        campaignDto.setCampaignTargetUrlDtos(campaignTargetUrlDtos);
        return campaignDto;

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public CampaignAnalysisLogDto getCampaignSearchAnalysisDetail(Long id, String startDate, String endDate) {
        List<CampaignAnalysisLogDto> list = campaignAnalysisSupportRepository.getDetailAnalysisData(id, startDate, endDate);
        boolean isMobile = false;
        CampaignAnalysisLogDto returnDto = new CampaignAnalysisLogDto(0);
        for(CampaignAnalysisLogDto dto : list){
            if(!"P".equalsIgnoreCase(dto.getDeviceType())){
                isMobile = true;
                returnDto.setTotalMoCnt(returnDto.getTotalMoCnt() + 1);
            }else{
                isMobile = false;
                returnDto.setTotalCnt(returnDto.getTotalCnt() + 1);
            }
            switch (dto.getEventType()){
                case "SHOW" :
                    if(isMobile) returnDto.setViewMoCnt(returnDto.getViewMoCnt() + 1);
                    else returnDto.setViewCnt(returnDto.getViewCnt() + 1);
                    break;
                case "LINKCLICK" :
                    if(isMobile) returnDto.setLinkClickMoCnt(returnDto.getLinkClickMoCnt() + 1);
                    else returnDto.setLinkClickCnt(returnDto.getLinkClickCnt() + 1);
                    break;
                case "CONTACT" :
                    if(isMobile) returnDto.setContactMoCnt(returnDto.getContactMoCnt() + 1);
                    else returnDto.setContactCnt(returnDto.getContactCnt() + 1);
                    break;
                case "SIGNUP" :
                    if(isMobile) returnDto.setSignUpMoCnt(returnDto.getSignUpMoCnt() + 1);
                    else returnDto.setSignUpCnt(returnDto.getSignUpCnt() + 1);
                    break;
                case "PHONECLICK" :
                    if(isMobile) returnDto.setPhoneClickMoCnt(returnDto.getPhoneClickMoCnt() + 1);
                    else returnDto.setPhoneClickCnt(returnDto.getPhoneClickCnt() + 1);
                    break;
                case "CLOSECLICK" :
                    if(isMobile) returnDto.setCloseClickMoCnt(returnDto.getCloseClickMoCnt() + 1);
                    else returnDto.setCloseClickCnt(returnDto.getCloseClickCnt() + 1);
                    break;
                case "VISIT" :
                    if(isMobile) returnDto.setVisitMoCnt(returnDto.getVisitMoCnt() + 1);
                    else returnDto.setVisitCnt(returnDto.getVisitCnt() + 1);
                    break;
            }

            if(isMobile) returnDto.setExpTimeMoAvg(returnDto.getExpTimeMoAvg() + dto.getExpTime());
            else returnDto.setExpTimeAvg(returnDto.getExpTimeAvg() + dto.getExpTime());
        }

        return returnDto;
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<CampaignAnalysisLogDto> getMultiCampaignSearchAnalysisDetail(Long id, String startDate, String endDate) {
        Integer multiCount = campaignMessageMultiRepository.countByCaNo(id);
        List<CampaignAnalysisLogDto> list = campaignAnalysisSupportRepository.getDetailAnalysisData(id, startDate, endDate);
        log.debug("====== getMultiCampaignSearchAnalysisDetail size ====== {}", list.size());
        log.debug("====== getMultiCampaignSearchAnalysisDetail ====== {}", list);
        Map<Integer, CampaignAnalysisLogDto> multiNoMap = new HashMap<>();

        List<Integer> multiNoList = new ArrayList<>();
        for (int i=0; i<=multiCount; i++) {
            multiNoMap.computeIfAbsent(i, k -> new CampaignAnalysisLogDto(0));
            multiNoList.add(i);
        }

        for (CampaignAnalysisLogDto dto : list) {
            if (!multiNoList.contains(dto.getMultiNo())) continue;

            // Device type에 따라 isMobile 플래그 설정
            boolean isMobile = !"P".equalsIgnoreCase(dto.getDeviceType());

            // 모바일 또는 PC에 따라 TotalCnt 또는 TotalMoCnt 증가
            if (isMobile) {
                multiNoMap.get(dto.getMultiNo()).setTotalMoCnt(multiNoMap.get(dto.getMultiNo()).getTotalMoCnt() + 1);
            } else {
                multiNoMap.get(dto.getMultiNo()).setTotalCnt(multiNoMap.get(dto.getMultiNo()).getTotalCnt() + 1);
            }

            // Event type에 따른 카운트 증가
            switch (dto.getEventType()) {
                // CTA 버튼/링크 클릭수
                case "LINKCLICK":
                    if (isMobile) {
                        multiNoMap.get(dto.getMultiNo()).setLinkClickMoCnt(multiNoMap.get(dto.getMultiNo()).getLinkClickMoCnt() + 1);
                    } else {
                        multiNoMap.get(dto.getMultiNo()).setLinkClickCnt(multiNoMap.get(dto.getMultiNo()).getLinkClickCnt() + 1);
                    }
                    break;
                // 가입상담 신청 버튼 클릭수
                case "CONTACT":
                    if (isMobile) {
                        multiNoMap.get(dto.getMultiNo()).setContactMoCnt(multiNoMap.get(dto.getMultiNo()).getContactMoCnt() + 1);
                    } else {
                        multiNoMap.get(dto.getMultiNo()).setContactCnt(multiNoMap.get(dto.getMultiNo()).getContactCnt() + 1);
                    }
                    break;
                // 바로가입 버튼 클릭수
                case "SIGNUP":
                    if (isMobile) {
                        multiNoMap.get(dto.getMultiNo()).setSignUpMoCnt(multiNoMap.get(dto.getMultiNo()).getSignUpMoCnt() + 1);
                    } else {
                        multiNoMap.get(dto.getMultiNo()).setSignUpCnt(multiNoMap.get(dto.getMultiNo()).getSignUpCnt() + 1);
                    }
                    break;
                // 가입상담 전화 버튼 클릭수
                case "PHONECLICK":
                    if (isMobile) {
                        multiNoMap.get(dto.getMultiNo()).setPhoneClickMoCnt(multiNoMap.get(dto.getMultiNo()).getPhoneClickMoCnt() + 1);
                    } else {
                        multiNoMap.get(dto.getMultiNo()).setPhoneClickCnt(multiNoMap.get(dto.getMultiNo()).getPhoneClickCnt() + 1);
                    }
                    break;
            }
        }

        return new ArrayList<>(multiNoMap.values());
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<CampaignDto> getCampaignAnalysisExcel(List<Long> id) {

        List<CampaignDto> campaignDtos = campaignRepository.findByIdIn(id).stream().map(campaignMapper::toDto).collect(Collectors.toList());

        for(CampaignDto campaignDto : campaignDtos) {
            CampaignMessageDto campaignMessageDto = campaignMessageMapper.toDto(campaignMessageRepository.findByCaNoAndUseTfAndDelTf(
                    Campaign.builder().id(campaignDto.getId()).build(), "Y", "N"));
            campaignDto.setCampaignMessageDto(campaignMessageDto);

            List<CampaignExposureTimeDto> campaignExposureTimeDtos =
                    campaignExposureTimeRepository.findByCaNoOrderByStartTime(Campaign.builder().id(campaignDto.getId()).build()).stream().map(campaignExposureTimeMapper::toDtoNoLazy).collect(Collectors.toList());
            List<CampaignExposureUrlDto> campaignExposureUrlDtos =
                    campaignExposureUrlRepository.findByCaNo(Campaign.builder().id(campaignDto.getId()).build()).stream().map(campaignExposureUrlMapper::toDtoNoLazy).collect(Collectors.toList());
            List<CampaignRevisitUrlDto> campaignRevisitUrlDtos =
                    campaignRevisitUrlRepository.findByCaNo(Campaign.builder().id(campaignDto.getId()).build()).stream().map(campaignRevisitUrlMapper::toDtoNoLazy).collect(Collectors.toList());
            List<CampaignSpecUrlDto> campaignSpecUrlDtos =
                    campaignSpecUrlRepository.findByCaNo(Campaign.builder().id(campaignDto.getId()).build()).stream().map(campaignSpecUrlMapper::toDtoNoLazy).collect(Collectors.toList());
            List<CampaignTargetUrlDto> campaignTargetUrlDtos =
                    campaignTargetUrlRepository.findByCaNo(Campaign.builder().id(campaignDto.getId()).build()).stream().map(campaignTargetUrlMapper::toDtoNoLazy).collect(Collectors.toList());
            Collections.sort(campaignExposureUrlDtos, Collections.reverseOrder());
            Collections.sort(campaignSpecUrlDtos, Collections.reverseOrder());
            campaignDto.setCampaignExposureTimeDtos(campaignExposureTimeDtos);
            campaignDto.setCampaignExposureUrlDtos(campaignExposureUrlDtos);
            campaignDto.setCampaignRevisitUrlDtos(campaignRevisitUrlDtos);
            campaignDto.setCampaignSpecUrlDtos(campaignSpecUrlDtos);
            campaignDto.setCampaignTargetUrlDtos(campaignTargetUrlDtos);

            List<CodeDetailDto> codeDetailDtos = commCodeUtils.getCodeList("TIME_SUB");
            List<String> tmpTimeDtos = new ArrayList<String>();
            String tmpEndTime = "";
            String tmpStartTimeNm = "";
            String tmpEndTimeNm = "";
            int tmpCnt = 0;
            for(CampaignExposureTimeDto campaignExposureTimeDto : campaignExposureTimeDtos) {
                tmpCnt++;
                for(CodeDetailDto codeDetailDto : codeDetailDtos) {
                    if(codeDetailDto.getDcodeExtValue1().equals(campaignExposureTimeDto.getStartTime())) {
                        if(!tmpEndTime.equals(campaignExposureTimeDto.getStartTime())) {
                            if(!"".equals(tmpEndTime)) {
                                tmpTimeDtos.add(tmpStartTimeNm + tmpEndTimeNm);
                            }
                            tmpStartTimeNm = codeDetailDto.getDcodeNm().split("-")[0].trim();
                            tmpEndTimeNm = " - " + codeDetailDto.getDcodeNm().split("-")[1].trim();
                        } else {
                            tmpEndTimeNm = " - " + codeDetailDto.getDcodeNm().split("-")[1].trim();
                        }

                        tmpEndTime = campaignExposureTimeDto.getEndTime();

                        if(campaignExposureTimeDtos.size() == tmpCnt) {
                            tmpTimeDtos.add(tmpStartTimeNm + tmpEndTimeNm);
                        }
                    }
                }
            }
            campaignDto.setCaTimes(tmpTimeDtos);

            Float totalAvg = campaignLogRepository.getTotalAvg(campaignDto.getId());
            Float moAvg = campaignLogRepository.getAvg(campaignDto.getId(), "M");
            Float pcAvg = campaignLogRepository.getAvg(campaignDto.getId(), "P");
            campaignDto.setTotalExpAvg(Float.parseFloat(String.format("%.2f", (totalAvg == null)? 0 : totalAvg)));
            campaignDto.setMoExpAvg(Float.parseFloat(String.format("%.2f", (moAvg == null)? 0 : moAvg)));
            campaignDto.setPcExpAvg(Float.parseFloat(String.format("%.2f", (pcAvg == null)? 0 : pcAvg)));
            campaignDto.setCaMsgTypeNm(commCodeUtils.getCampaignBaseInfo(campaignDto.getCaMsgType()).getTitle());
            campaignDto.setTgType01Nm(commCodeUtils.getCodeInfo("CAM_TARGET1", campaignDto.getTgType01()).getDcodeNm());
            if("FIXED".equals(campaignDto.getTgType01())) {
                campaignDto.setTg1SubNm(commCodeUtils.getCodeInfo("CAM_TARGET2", campaignDto.getTg1Sub()).getDcodeNm());
            } else if("RETURN".equals(campaignDto.getTgType01())) {
                campaignDto.setTg1SubNm(commCodeUtils.getCodeInfo("TAR1_RETURN", campaignDto.getTg1Sub()).getDcodeNm());
            }
            if("FIXED".equals(campaignDto.getTgType01())) {
                campaignDto.setTgType02Nm(commCodeUtils.getCodeInfo("CAM_TARGET1", campaignDto.getTgType02()).getDcodeNm());
                if("RETURN".equals(campaignDto.getTgType02())) {
                    campaignDto.setTg2SubNm(commCodeUtils.getCodeInfo("TAR1_RETURN", campaignDto.getTg2Sub()).getDcodeNm());
                }
            } else {
                campaignDto.setTgType02Nm("");
                campaignDto.setTg2SubNm(commCodeUtils.getCodeInfo("CAM_TARGET2", campaignDto.getTg2Sub()).getDcodeNm());
            }
            campaignDto.setCaTimeNm(commCodeUtils.getCodeInfo("CAM_EXPO_TIME", campaignDto.getCaTime()).getDcodeNm());
            campaignDto.setCaPlaceNm(commCodeUtils.getCodeInfo("CAM_PLACE", campaignDto.getCaPlace()).getDcodeNm());
            campaignDto.setCaViewPointNm(commCodeUtils.getCodeInfo("CAM_VIEW_POINT", campaignDto.getCaViewPoint()).getDcodeNm());
            if("PERCENT".equals(campaignDto.getCaViewPoint())) {
                campaignDto.setViewPointSubNm(commCodeUtils.getCodeInfo("VIEW_PERCENT"
                        , campaignDto.getViewPointSub()).getDcodeNm());
            } else if("NONE_MOVE".equals(campaignDto.getCaViewPoint()) || "LOADING".equals(campaignDto.getCaViewPoint())) {
                campaignDto.setViewPointSubNm(commCodeUtils.getCodeInfo("VIEW_SECOND"
                        , campaignDto.getViewPointSub()).getDcodeNm());
            } else if("BEFORE_OUT".equals(campaignDto.getCaViewPoint())) {
                campaignDto.setViewPointSubNm(commCodeUtils.getCodeInfo("VIEW_OUT"
                        , campaignDto.getViewPointSub()).getDcodeNm());
            } else {
                campaignDto.setViewPointSubNm("");
            }
            campaignDto.setExposureLimitCntNm(commCodeUtils.getCodeInfo("CAM_USING", campaignDto.getExposureLimitCnt()).getDcodeNm());
            campaignDto.setCaPurposeNm(commCodeUtils.getCodeInfo("CAM_PURPOSE", campaignDto.getCaPurpose()).getDcodeNm());

            log.info("campaignDto >>> {}", campaignDto);
        }

        return campaignDtos;

    }

}
