package com.diegofdez.springbootinaction.readingList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ReadingListController {
	private AmazonProperties amazonProperties;
	private ReadingListRepository readingListRepository;
	
	@Autowired
	public ReadingListController(
			ReadingListRepository readingListRepository,
			AmazonProperties amazonProperties) {
		this.readingListRepository = readingListRepository;
		this.amazonProperties = amazonProperties;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String readerBooks(String reader, Model model) {
		List<Book> readingList = readingListRepository.findByReader(reader);
		
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("amazonId", amazonProperties.getAssociateId());
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
