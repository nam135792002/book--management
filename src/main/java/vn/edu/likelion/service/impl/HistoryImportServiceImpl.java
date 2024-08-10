package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.Book;
import vn.edu.likelion.entity.HistoryImport;
import vn.edu.likelion.model.book.BookResponse;
import vn.edu.likelion.repository.HistoryImportRepository;
import vn.edu.likelion.service.HistoryImportInterface;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoryImportServiceImpl implements HistoryImportInterface {

    @Autowired private HistoryImportRepository historyImportRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public List<BookResponse> searchByDate(LocalDate startDate, LocalDate endDate) {
        List<HistoryImport> listHistoryImports = historyImportRepository.findAll();

        return listHistoryImports.stream()
                .filter(historyImport -> !historyImport.getDateImport().isBefore(startDate)
                                            && !historyImport.getDateImport().isAfter(endDate))
                .map(historyImport -> modelMapper.map(historyImport.getBook(),BookResponse.class))
                .distinct()
                .toList();
    }
}
