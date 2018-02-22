package com.diegofdez.springbootinaction.readingList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
@ConfigurationProperties(prefix="amazon") // All amazon.* properties from application.properties will be sent to setters in this class
public class ReadingListController {
	private String associateId;
	private ReadingListRepository readingListRepository;
	
	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository) {
		this.readingListRepository = readingListRepository;
	}
	
	// The value is set because of ConfigurationProperties annotation
	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String readerBooks(String reader, Model model) {
		List<Book> readingList = readingListRepository.findByReader(reader);
		
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("amazonId", associateId);
		}
		
		return "readingList";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addToReadingList(String reader, Book book) {
		book.setReader(reader);
		readingListRepository.save(book);
		return "redirect:/";
	}
}
