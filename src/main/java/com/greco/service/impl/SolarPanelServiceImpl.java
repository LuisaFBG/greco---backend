package com.greco.service.impl;

import com.greco.exception.NotFoundException;
import com.greco.messages.GenericCheckingMessage;
import com.greco.model.SolarPanel;
import com.greco.repository.SolarPanelRepository;
import com.greco.service.SolarPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service("solarPanelService")
public class SolarPanelServiceImpl implements SolarPanelService {
    @Autowired
    SolarPanelRepository solarPanelRepository;

    @Override
    public SolarPanel findById(Long id) {
        Optional<SolarPanel> optional = solarPanelRepository.findById(id);
        if(!optional.isPresent())
             throw new NotFoundException(GenericCheckingMessage.ENTITY_NOT_FOUND.toString());
        return optional.get();
    }

    @Override
    public SolarPanel insert(SolarPanel solarPanel) {
        return solarPanelRepository.save(solarPanel);
    }

    @Override
    public SolarPanel update(SolarPanel solarPanel) {
        return solarPanelRepository.save(solarPanel);
    }

    @Override
    public Page<SolarPanel> findAll(Specification<SolarPanel> specs, Pageable pageable) {
        return solarPanelRepository.findAll(specs, pageable);
    }
}
