package com.example.myweb.service;

import com.example.myweb.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookSearchService {

    @Value("${aladin.api.key}")
    private String ttbKey;

    private static final String API_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";

    public List<Book> getBestsellers() {
        String url = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + ttbKey + "&QueryType=Bestseller&MaxResults=5&SearchTarget=Book&Cover=Big&output=xml";
        return fetchBooksFromApi(url);
    }

    public List<Book> getEditorChoices() {
        String url = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + ttbKey + "&QueryType=ItemEditorChoice&MaxResults=5&SearchTarget=Book&Cover=Big&output=xml";
        return fetchBooksFromApi(url);
    }

    public List<Book> getNewSpecials() {
        String url = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + ttbKey + "&QueryType=ItemNewSpecial&MaxResults=5&SearchTarget=Book&Cover=Big&output=xml";
        return fetchBooksFromApi(url);
    }

    public Page<Book> searchBooks(String query, String queryType, String sort, PageRequest pageRequest) {
        String url = API_URL + "?ttbkey=" + ttbKey + "&Query=" + query + "&QueryType=" + queryType + "&MaxResults=100&Sort=" + sort + "&SearchTarget=Book&Cover=Big&output=xml";
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(url, String.class);

        List<Book> books = parseXmlResponse(xmlResponse);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), books.size());
        return new PageImpl<>(books.subList(start, end), pageRequest, books.size());
    }

    public Book getBookDetail(String title, String author) {
        String url = API_URL + "?ttbkey=" + ttbKey + "&Query=" + title + "&QueryType=Title&MaxResults=1&SearchTarget=Book&Cover=Big&output=xml";
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(url, String.class);

        List<Book> books = parseXmlResponse(xmlResponse);
        if (!books.isEmpty()) {
            return books.get(0);
        }
        return null;
    }

    private List<Book> fetchBooksFromApi(String url) {
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse = restTemplate.getForObject(url, String.class);

        List<Book> books = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlResponse.getBytes()));

            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                Element item = (Element) itemList.item(i);
                String title = item.getElementsByTagName("title").item(0).getTextContent();
                String author = item.getElementsByTagName("author").item(0).getTextContent();
                String cover = item.getElementsByTagName("cover").item(0).getTextContent();
                String category = item.getElementsByTagName("categoryName").item(0).getTextContent();
                String publisher = item.getElementsByTagName("publisher").item(0).getTextContent();
                String pubDate = item.getElementsByTagName("pubDate").item(0).getTextContent();
                String isbn = item.getElementsByTagName("isbn").item(0).getTextContent();
                String price = item.getElementsByTagName("priceStandard").item(0).getTextContent();
                String description = item.getElementsByTagName("description").item(0).getTextContent();
                String link = item.getElementsByTagName("link").item(0).getTextContent();

                books.add(new Book(title, author, cover, category, publisher, pubDate, isbn, price, description, link));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    private List<Book> parseXmlResponse(String xmlResponse) {
        List<Book> books = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlResponse.getBytes()));

            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                Element item = (Element) itemList.item(i);
                String title = item.getElementsByTagName("title").item(0).getTextContent();
                String author = item.getElementsByTagName("author").item(0).getTextContent();
                String cover = item.getElementsByTagName("cover").item(0).getTextContent();
                String category = item.getElementsByTagName("categoryName").item(0).getTextContent();
                String publisher = item.getElementsByTagName("publisher").item(0).getTextContent();
                String pubDate = item.getElementsByTagName("pubDate").item(0).getTextContent();
                String isbn = item.getElementsByTagName("isbn").item(0).getTextContent();
                String price = item.getElementsByTagName("priceStandard").item(0).getTextContent();
                String description = item.getElementsByTagName("description").item(0).getTextContent();
                String link = item.getElementsByTagName("link").item(0).getTextContent();

                books.add(new Book(title, author, cover, category, publisher, pubDate, isbn, price, description, link));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
