package com.rest_library.service;

import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import com.rest_library.exceptions.EmailAlreadyExistException;
import com.rest_library.exceptions.ResourceNotFoundException;
import com.rest_library.mapper.ReaderMapper;
import com.rest_library.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReaderServiceImpl implements ReaderService{

    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;


    @Override
    public List<ReaderDto> findAllReadersDto() {
        return readerRepository.findAll().stream()
                .map(readerMapper::mapToReaderDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReaderDto findReaderDto(Long id) {
        Reader tempReader = readerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", id));

        return readerMapper.mapToReaderDto(tempReader);
    }

//    @Override
//    public ReaderDto addNewReaderDto(ReaderDto readerDto) {
//        Optional<Reader> optionalReader = readerRepository.findReaderByEmail(readerDto.getEmail());
//        if(optionalReader.isPresent()) {
//            throw new EmailAlreadyExistException("Email already exists in the database");
//        }
//        Reader reader = readerMapper.mapToReader(readerDto);
//        Reader savedReader = readerRepository.save(reader);
//        return readerMapper.mapToReaderDto(savedReader);
//    }

    @Override
    public ReaderDto saveReaderDto(ReaderDto readerDto) {
        Optional<Reader> optionalReader = readerRepository.findReaderByEmail(readerDto.getEmail());
        if(optionalReader.isPresent()) {
            throw new EmailAlreadyExistException("Email already exists in the database");
        }

        return readerMapper.mapToReaderDto(readerRepository.save(readerMapper.mapToReader(readerDto)));
    }

    @Override
    public ReaderDto updateReaderDto(ReaderDto readerDto, Long id) {
        Reader updatedReader = readerRepository.findById(id)
                .map(reader -> {
                    reader.setEmail(readerDto.getEmail());
                    reader.setFirstName(readerDto.getFirstName());
                    reader.setLastName(readerDto.getLastName());
                    reader.setStartingDate(readerDto.getStartingDate());
                    return readerRepository.save(reader);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Reader", "id", id));

        return readerMapper.mapToReaderDto(updatedReader);
    }

    @Override
    public void deleteReaderDto(Long id) {
        Optional<Reader> optionalReader = readerRepository.findById(id);
        if (optionalReader.isPresent()) {
            readerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Reader", "id", id);
        }
    }
}
