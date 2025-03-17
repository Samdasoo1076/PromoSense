package com.skbroadband.doms.web.service;


import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.global.utils.CommCodeUtils;
import com.skbroadband.doms.web.dto.*;
import com.skbroadband.doms.web.entity.Campaign;
import com.skbroadband.doms.web.entity.CampaignMessage;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * @author : 이현민
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : AccountLogService
 * @Program :
 * @Date : 2022-12-08
 * @Comment :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignListService {

    private final CampaignRepository campaignRepository;
    private final CampaignMessageRepository campaignMessageRepository;

    private final CampaignExposureTimeRepository campaignExposureTimeRepository;
    private final CampaignExposureUrlRepository campaignExposureUrlRepository;

    private final CampaignMapper campaignMapper;

    private final CommCodeUtils commCodeUtils;

    private final CampaignListSupportRepository campaignListSupportRepository;

    private final CampaignExposureTimeMapper campaignExposureTimeMapper;
    private final CampaignExposureUrlMapper campaignExposureUrlMapper;
    private final CampaignRevisitUrlMapper campaignRevisitUrlMapper;
    private final CampaignSpecUrlMapper campaignSpecUrlMapper;
    private final CampaignTargetUrlMapper campaignTargetUrlMapper;

    private final CampaignRevisitUrlRepository campaignRevisitUrlRepository;
    private final CampaignSpecUrlRepository campaignSpecUrlRepository;
    private final CampaignTargetUrlRepository campaignTargetUrlRepository;

    private final CampaignMessageMapper campaignMessageMapper;

    private final CampaignLogRepository campaignLogRepository;

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Page<CampaignDto> findCampaigns(String keyword, String startDate, String endDate,String caGubun, String caFlag, Pageable pageable) {

        Page<CampaignDto> campaignDtos = campaignListSupportRepository.findAll(keyword, startDate, endDate, caGubun, caFlag, pageable);
//        campaignDtos.forEach(caDto -> {
//            if(!keyword.equals("")) {
//                caDto.setCaName(
//                        caDto.getCaName().replaceAll(keyword, "<mark class=\"em accent-01\">" + keyword + "</mark>")
//                );
//            }
//        });

        return campaignDtos;
    }

    @Transactional(value = "webTransactionManager")
    public void changeCaState(Long caNo, String useTf, Long upAdm ) {

        Campaign campaign = campaignRepository.findById(caNo).orElse(null);

        if(campaign == null) {
            throw new BadRequestException("변경 가능한 캠패인이 없습니다.");
        }else if(campaign.getCaState().equals("1")){
            throw new BadRequestException("변경 등록중인 캠패인은 on/off 변경이 불가능 합니다.");
        }else{
            campaign.changeCaState(useTf, upAdm);
        }

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Map tabCount(String keyword, String startDate, String endDate, String caGubun) {
        return campaignListSupportRepository.tabCount(keyword, startDate, endDate, caGubun);
    }

    @Transactional(value = "webTransactionManager")
    public void deleteCampaign(Long caNo, Long delAdm) {

        Campaign campaign = campaignRepository.findById(caNo).orElse(null);
        CampaignMessage campaignMessage = campaignMessageRepository.findByCaNoAndUseTfAndDelTf(campaign,"Y","N");

        if(campaign == null) {
            throw new BadRequestException("삭제 가능한 캠페인이 없습니다.");
        }
        if(campaignMessage != null){
            campaignMessage.deleteCampaignMessage(delAdm);
        }

        campaign.deleteCampaign(delAdm);

    }

    /*
    * 리스트 클릭시 요약정보 팝업데이터
    *
    * */
    @Transactional(value = "webTransactionManager", readOnly = true)
    public CampaignDto findBasePopup(Long caNo) {

        CampaignDto campaignDtos = campaignListSupportRepository.basePopup(caNo);

        return campaignDtos;
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Page<CampaignDto> findActiveCampaigns(String keyword, String startDate, String endDate, String caGubun, String caFlag, Pageable pageable) {

        Page<CampaignDto> campaignDtos = campaignListSupportRepository.findAll(keyword, startDate, endDate, caGubun, caFlag, pageable);

        return campaignDtos;
    }



    @Transactional(value = "webTransactionManager")
    public CampaignDto getCampaignInfo(Long id) {

        CampaignDto campaignDto = campaignRepository.findById(id).map(campaignMapper::toDto).orElse(null);
        CampaignMessageDto campaignMessageDto = campaignMessageMapper.toDto(campaignMessageRepository.findByCaNoAndUseTfAndDelTf(Campaign.builder().id(id).build(),
                "Y", "N"));

        //시작 <= 현재 and 끝 >= 현재
        //시작 <= 현재 and 끝 >= 현재   ==  s0002
        //시작 >= 현재 and 끝 <= 현재   ==  s0001
        //끝 < 현재   ==  s0004
        // 나머지 s9999
        if(campaignDto != null) {

            if(Instant.now().isBefore(campaignDto.getCaStartDate())){
                campaignDto.setCaStateNm("s0001");
            }else if(Instant.now().isAfter(campaignDto.getCaStartDate()) && Instant.now().isBefore(campaignDto.getCaEndDate())){
                if("N".equals(campaignDto.getUseTf())){
                    campaignDto.setCaStateNm("s0003");
                }else{
                    campaignDto.setCaStateNm("s0002");
                }
            }else if(Instant.now().isAfter(campaignDto.getCaEndDate())){
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
                    campaignDto.setCaStateNm("s0004");
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


//    @CacheEvict(cacheNames = {"aclCache", "caNoCache"}, allEntries=true)
    @Transactional(value = "webTransactionManager")
    public void changeViewOrder(Long[] caNos, Long admNo) {
        AtomicInteger index = new AtomicInteger();
        Arrays.stream(caNos).forEach(caNo -> campaignRepository.changeViewOrder(caNo, index.incrementAndGet(), admNo));
//        Arrays.stream(caNos).forEach(caNo -> campaignRepository.changeViewOrder(caNo, index.getAndIncrement(), admNo));
    }



}
