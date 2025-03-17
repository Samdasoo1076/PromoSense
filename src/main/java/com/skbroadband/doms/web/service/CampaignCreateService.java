package com.skbroadband.doms.web.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skbroadband.doms.web.dto.*;
import com.skbroadband.doms.web.entity.*;
import com.skbroadband.doms.web.mapper.*;
import com.skbroadband.doms.web.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampaignCreateService {

    private final CampaignMapper campaignMapper;
    private final CampaignExposureTimeMapper campaignExposureTimeMapper;
    private final CampaignExposureUrlMapper campaignExposureUrlMapper;
    private final CampaignRevisitUrlMapper campaignRevisitUrlMapper;
    private final CampaignSpecUrlMapper campaignSpecUrlMapper;
    private final CampaignTargetUrlMapper campaignTargetUrlMapper;
    private final CampaignMessageMapper campaignMessageMapper;
    private final CampaignMessageHistoryMapper campaignMessageHistoryMapper;
    private final CampaignRepository campaignRepository;
    private final CampaignExposureTimeRepository campaignExposureTimeRepository;
    private final CampaignExposureUrlRepository campaignExposureUrlRepository;
    private final CampaignRevisitUrlRepository campaignRevisitUrlRepository;
    private final CampaignSpecUrlRepository campaignSpecUrlRepository;
    private final CampaignTargetUrlRepository campaignTargetUrlRepository;
    private final CampaignMessageRepository campaignMessageRepository;
    private final CampaignMessageHistoryRepository campaignMessageHistoryRepository;
    private final CampaignMessageMultiRepository campaignMessageMultiRepository;
    private final CampaignMessageMultiMapper campaignMessageMultiMapper;


    @Transactional(value = "webTransactionManager")
    public CampaignDto getCampaignInfo(Long id) {

        CampaignDto campaignDto = campaignRepository.findById(id).map(campaignMapper::toDto).orElse(null);
        List<CampaignExposureTimeDto> campaignExposureTimeDtos =
                campaignExposureTimeRepository.findByCaNo(Campaign.builder().id(id).build()).stream().map(campaignExposureTimeMapper::toDtoNoLazy).collect(Collectors.toList());
        List<CampaignExposureUrlDto> campaignExposureUrlDtos =
                campaignExposureUrlRepository.findByCaNo(Campaign.builder().id(id).build()).stream().map(campaignExposureUrlMapper::toDtoNoLazy).collect(Collectors.toList());
        List<CampaignRevisitUrlDto> campaignRevisitUrlDtos =
                campaignRevisitUrlRepository.findByCaNo(Campaign.builder().id(id).build()).stream().map(campaignRevisitUrlMapper::toDtoNoLazy).collect(Collectors.toList());
        List<CampaignSpecUrlDto> campaignSpecUrlDtos =
                campaignSpecUrlRepository.findByCaNo(Campaign.builder().id(id).build()).stream().map(campaignSpecUrlMapper::toDtoNoLazy).collect(Collectors.toList());
        List<CampaignTargetUrlDto> campaignTargetUrlDtos =
                campaignTargetUrlRepository.findByCaNo(Campaign.builder().id(id).build()).stream().map(campaignTargetUrlMapper::toDtoNoLazy).collect(Collectors.toList());

        if(campaignExposureUrlDtos.stream().filter(data -> data.getGubun().equals("P")).collect(Collectors.toList()).size() == 0)
            campaignExposureUrlDtos.add(CampaignExposureUrlDto.builder().gubun("P").build());
        if(campaignExposureUrlDtos.stream().filter(data -> data.getGubun().equals("M")).collect(Collectors.toList()).size() == 0)
            campaignExposureUrlDtos.add(CampaignExposureUrlDto.builder().gubun("M").build());

        if(campaignRevisitUrlDtos.stream().filter(data -> data.getGubun().equals("P")).collect(Collectors.toList()).size() == 0)
            campaignRevisitUrlDtos.add(CampaignRevisitUrlDto.builder().gubun("P").build());
        if(campaignRevisitUrlDtos.stream().filter(data -> data.getGubun().equals("M")).collect(Collectors.toList()).size() == 0)
            campaignRevisitUrlDtos.add(CampaignRevisitUrlDto.builder().gubun("M").build());

        if(campaignSpecUrlDtos.stream().filter(data -> data.getGubun().equals("P")).collect(Collectors.toList()).size() == 0)
            campaignSpecUrlDtos.add(CampaignSpecUrlDto.builder().gubun("P").build());
        if(campaignSpecUrlDtos.stream().filter(data -> data.getGubun().equals("M")).collect(Collectors.toList()).size() == 0)
            campaignSpecUrlDtos.add(CampaignSpecUrlDto.builder().gubun("M").build());

        if(campaignTargetUrlDtos.stream().filter(data -> data.getGubun().equals("P")).collect(Collectors.toList()).size() == 0)
            campaignTargetUrlDtos.add(CampaignTargetUrlDto.builder().gubun("P").build());
        if(campaignTargetUrlDtos.stream().filter(data -> data.getGubun().equals("M")).collect(Collectors.toList()).size() == 0)
            campaignTargetUrlDtos.add(CampaignTargetUrlDto.builder().gubun("M").build());

        Collections.sort(campaignExposureUrlDtos, Collections.reverseOrder());
        Collections.sort(campaignSpecUrlDtos, Collections.reverseOrder());

        campaignDto.setCampaignExposureTimeDtos(campaignExposureTimeDtos);
        campaignDto.setCampaignExposureUrlDtos(campaignExposureUrlDtos);
        campaignDto.setCampaignRevisitUrlDtos(campaignRevisitUrlDtos);
        campaignDto.setCampaignSpecUrlDtos(campaignSpecUrlDtos);
        campaignDto.setCampaignTargetUrlDtos(campaignTargetUrlDtos);

        return campaignDto;

    }
    @Transactional(value = "webTransactionManager")
    public Long saveBaseCampaign(CampaignDto campaignDto) {

        Campaign campaign = campaignMapper.toEntities(campaignDto);

        if(campaignDto.getCampaignExposureTimeDtos() != null) {
            for(CampaignExposureTimeDto campaignExposureTimeDto : campaignDto.getCampaignExposureTimeDtos()) {
                CampaignExposureTime campaignExposureTime = campaignExposureTimeMapper.toEntity(campaignExposureTimeDto);
                campaign.addCampaignExposureTime(campaignExposureTime);
            }
        }
        if(campaignDto.getCampaignExposureUrlDtos() != null) {
            for(CampaignExposureUrlDto campaignExposureUrlDto : campaignDto.getCampaignExposureUrlDtos()) {
                CampaignExposureUrl campaignExposureUrl = campaignExposureUrlMapper.toEntity(campaignExposureUrlDto);
                campaign.addCampaignExposureUrl(campaignExposureUrl);
            }
        }
        if(campaignDto.getCampaignRevisitUrlDtos() != null) {
            for(CampaignRevisitUrlDto campaignRevisitUrlDto : campaignDto.getCampaignRevisitUrlDtos()) {
                CampaignRevisitUrl campaignRevisitUrl = campaignRevisitUrlMapper.toEntity(campaignRevisitUrlDto);
                campaign.addCampaignRevisitUrl(campaignRevisitUrl);
            }
        }
        if(campaignDto.getCampaignSpecUrlDtos() != null) {
            for(CampaignSpecUrlDto campaignSpecUrlDto : campaignDto.getCampaignSpecUrlDtos()) {
                CampaignSpecUrl campaignSpecUrl = campaignSpecUrlMapper.toEntity(campaignSpecUrlDto);
                campaign.addCampaignSpecUrl(campaignSpecUrl);
            }
        }
        if(campaignDto.getCampaignTargetUrlDtos() != null) {
            for(CampaignTargetUrlDto campaignTargetUrlDto : campaignDto.getCampaignTargetUrlDtos()) {
                CampaignTargetUrl campaignTargetUrl = campaignTargetUrlMapper.toEntity(campaignTargetUrlDto);
                campaign.addCampaignTargetUrl(campaignTargetUrl);
            }
        }

        Long id = campaignRepository.save(campaign).getId();

        return id;

    }

    @Transactional(value = "webTransactionManager")
    public void modifyBaseCampaign(CampaignDto campaignDto) {
        CampaignMessage campaignMessage = campaignMessageRepository.findByCaNoAndUseTfAndDelTf(Campaign.builder().id(campaignDto.getId()).build(),
                "Y", "N");
        if(campaignMessage != null) {
            if(!String.valueOf(campaignDto.getCaMsgType()).equals(campaignMessage.getMsgType())) {
                campaignMessage.deleteCampaignMessage(campaignDto.getUpAdm());
                campaignDto.setCaState("1");
            }
        }

        Campaign campaign = campaignMapper.toEntities(campaignDto);

        if(campaignDto.getCampaignExposureTimeDtos() != null) {
            for(CampaignExposureTimeDto campaignExposureTimeDto : campaignDto.getCampaignExposureTimeDtos()) {
                CampaignExposureTime campaignExposureTime = campaignExposureTimeMapper.toEntity(campaignExposureTimeDto);
                campaign.addCampaignExposureTime(campaignExposureTime);
            }
        }
        if(campaignDto.getCampaignExposureUrlDtos() != null) {
            for(CampaignExposureUrlDto campaignExposureUrlDto : campaignDto.getCampaignExposureUrlDtos()) {
                CampaignExposureUrl campaignExposureUrl = campaignExposureUrlMapper.toEntity(campaignExposureUrlDto);
                campaign.addCampaignExposureUrl(campaignExposureUrl);
            }
        }
        if(campaignDto.getCampaignRevisitUrlDtos() != null) {
            for(CampaignRevisitUrlDto campaignRevisitUrlDto : campaignDto.getCampaignRevisitUrlDtos()) {
                CampaignRevisitUrl campaignRevisitUrl = campaignRevisitUrlMapper.toEntity(campaignRevisitUrlDto);
                campaign.addCampaignRevisitUrl(campaignRevisitUrl);
            }
        }
        if(campaignDto.getCampaignSpecUrlDtos() != null) {
            for(CampaignSpecUrlDto campaignSpecUrlDto : campaignDto.getCampaignSpecUrlDtos()) {
                CampaignSpecUrl campaignSpecUrl = campaignSpecUrlMapper.toEntity(campaignSpecUrlDto);
                campaign.addCampaignSpecUrl(campaignSpecUrl);
            }
        }
        if(campaignDto.getCampaignTargetUrlDtos() != null) {
            for(CampaignTargetUrlDto campaignTargetUrlDto : campaignDto.getCampaignTargetUrlDtos()) {
                CampaignTargetUrl campaignTargetUrl = campaignTargetUrlMapper.toEntity(campaignTargetUrlDto);
                campaign.addCampaignTargetUrl(campaignTargetUrl);
            }
        }

        campaignExposureTimeRepository.deleteByCaNo(Campaign.builder().id(campaignDto.getId()).build());
        campaignExposureUrlRepository.deleteByCaNo(Campaign.builder().id(campaignDto.getId()).build());
        campaignRevisitUrlRepository.deleteByCaNo(Campaign.builder().id(campaignDto.getId()).build());
        campaignSpecUrlRepository.deleteByCaNo(Campaign.builder().id(campaignDto.getId()).build());
        campaignTargetUrlRepository.deleteByCaNo(Campaign.builder().id(campaignDto.getId()).build());

        campaignRepository.save(campaign);
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public Long getCaMsgTypeId(Long id) {
        Long caMsgTypeId = campaignRepository.findById(id).get().getCaMsgType();

        return caMsgTypeId;
    }

    @Transactional(value = "webTransactionManager")
    public void saveMessageCampaign(CampaignMessageDto campaignMessageDto) {
        CampaignMessage campaignMessage = campaignMessageMapper.toEntity(campaignMessageDto);

        CampaignMessage saveMessage = campaignMessageRepository.save(campaignMessage);

        if (
                "8".equals(campaignMessageDto.getMsgType()) &&
                campaignMessageDto.getMultiList() != null
        ) {
            String multiListJson = campaignMessageDto.getMultiList();

            ObjectMapper objectMapper = new ObjectMapper();
            List<CampaignMessageMultiDto> multiList = null;
            try {
                multiList = objectMapper.readValue(multiListJson, new TypeReference<List<CampaignMessageMultiDto>>() {});
            } catch (IOException e) {
                e.printStackTrace();
                log.debug("multiList objectMapper Error {}", multiList);
            }

            campaignMessageMultiRepository.deleteCampaignMessageMultiByCaNo(campaignMessageDto.getCaNo().getId());

            if (multiList != null) {
                for (CampaignMessageMultiDto campaignMessageMultiDto : multiList) {
                    if (campaignMessageMultiDto.getCaNo() == null) {
                        campaignMessageMultiDto.setCaNo(saveMessage.getCaNo().getId());
                    }

                    if (
                        campaignMessageDto.getMsgImgNm01List() != null &&
                        campaignMessageDto.getMsgImgRnm01List() != null
                    ) {

                        log.debug("=== getMsgImgNm01List === {}", campaignMessageDto.getMsgImgNm01List());
                        log.debug("=== getMsgImgRnm01List === {}", campaignMessageDto.getMsgImgRnm01List());
                        int index = multiList.indexOf(campaignMessageMultiDto);
                        if (

                                !campaignMessageDto.getMsgImgNm01List().isEmpty() &&
                                index < campaignMessageDto.getMsgImgNm01List().size()
                        ) {
                            campaignMessageMultiDto.setMsgImgRnm01(campaignMessageDto.getMsgImgRnm01List().get(index));
                            campaignMessageMultiDto.setMsgImgNm01(campaignMessageDto.getMsgImgNm01List().get(index));
                        }
                    }

                    CampaignMessageMulti campaignMessageMulti = campaignMessageMultiMapper.toEntity(campaignMessageMultiDto);

                    campaignMessageMultiRepository.save(campaignMessageMulti);
                }
            }
        }

        if("Y".equals(campaignMessageDto.getMsgSaveTf())) {
            campaignMessageDto.setId(null);
            CampaignMessageHistory campaignMessageHistory = campaignMessageHistoryMapper.toHisEntity(campaignMessageDto);
            campaignMessageHistoryRepository.save(campaignMessageHistory);
        }

        Campaign campaign = campaignRepository.findById(campaignMessageDto.getCaNo().getId()).get();
        campaign.updateMsgStatus("2", campaignMessageDto.getUpAdm());
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<CampaignMessageHistoryDto> getHistoryMessage(String msgType) {
        return campaignMessageHistoryRepository.findByMsgTypeAndAndUseTfAndDelTf(msgType, "Y", "N").stream()
                .map(campaignMessageHistoryMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(value = "webTransactionManager")
    public void deleteHistory(List<CampaignMessageHistoryDto> campaignMessageHistoryDtos) {

        for(CampaignMessageHistoryDto campaignMessageHistoryDto : campaignMessageHistoryDtos) {
            campaignMessageHistoryRepository.deleteHistory(campaignMessageHistoryDto);
        }

    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public CampaignMessageDto getCampaignMessage(Long id) {
        Campaign campaign = Campaign.builder().id(id).build();
        return campaignMessageMapper.toDto(campaignMessageRepository.findByCaNoAndUseTfAndDelTf(campaign, "Y", "N"));
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public CampaignMessageDto getCampaignMessageHistory(Long hisId) {
        return campaignMessageHistoryMapper.toMsgDto(campaignMessageHistoryRepository.findById(hisId).get());
    }

    @Transactional(value = "webTransactionManager", readOnly = true)
    public List<CampaignMessageMultiDto> getCampaignMessageMulti(Long caNo) {
        return campaignMessageMultiRepository.findCampaignMessageMultiByCaNoOrderByMultiSeqAsc(caNo).stream()
                .map(campaignMessageMultiMapper::toDto).collect(Collectors.toList());
    }
}
