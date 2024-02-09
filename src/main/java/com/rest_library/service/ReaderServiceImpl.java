package com.rest_library.service;

import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import com.rest_library.exceptions.EmailAlreadyExistException;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.mapper.ReaderMapper;
import com.rest_library.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;


    @Override
    public List<ReaderDto> findAllReaders() {
        log.info("====>>>> findAllReaders() execution");
        return readerRepository.findAll().stream()
                .map(readerMapper::mapToReaderDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReaderDto findReader(Long id) {
        Reader tempReader = readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", id));

        log.info("====>>>> findReader() execution");
        return readerMapper.mapToReaderDto(tempReader);
    }

    @Override
    public ReaderDto saveReader(ReaderDto readerDto) {
        Optional<Reader> optionalReader = readerRepository.findReaderByEmail(readerDto.getEmail());
        if (optionalReader.isPresent()) {
            throw new EmailAlreadyExistException("Email already exists in the database");
        }

        log.info("====>>>> saveReader() execution");
        return readerMapper.mapToReaderDto(readerRepository.save(readerMapper.mapToReader(readerDto)));
    }

    @Override
    public ReaderDto updateReader(ReaderDto readerDto, Long id) {
        Reader updatedReader = readerRepository.findById(id)
                .map(reader -> {
                    reader.setEmail(readerDto.getEmail());
                    reader.setFirstName(readerDto.getFirstName());
                    reader.setLastName(readerDto.getLastName());
                    reader.setStartingDate(readerDto.getStartingDate());
                    log.info("====>>>> updateReader() execution");
                    return readerRepository.save(reader);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", id));

        return readerMapper.mapToReaderDto(updatedReader);
    }

    @Override
    public void deleteReader(Long id) {
        Optional<Reader> optionalReader = readerRepository.findById(id);
        if (optionalReader.isPresent()) {
            log.info("====>>>> deleteReader() execution");
            readerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Reader", "id", id);
        }
    }
}
