package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {

    @InjectMocks
    public TrelloMapper trelloMapper;

    List<TrelloListDto> listDtos1 = new ArrayList<>();
    List<TrelloListDto> listDtos2 = new ArrayList<>();
    List<TrelloBoardDto> boardDtos = new ArrayList<>();

    @Before
    public void init() {

        listDtos1.add(new TrelloListDto("1","list1",false));
        listDtos1.add(new TrelloListDto("2","list2",false));

        listDtos2.add(new TrelloListDto("3","list4",false));
        listDtos2.add(new TrelloListDto("4","list4",false));

        boardDtos.add(new TrelloBoardDto("1","board1",listDtos1));
        boardDtos.add(new TrelloBoardDto("2","board2",listDtos2));
    }

    @Test
    public void testMapToBoards(){
        //Given
        //When
        List<TrelloList> testList = new ArrayList<>();
        testList.add(new TrelloList("1", "list1", false));
        TrelloBoard testBoard = new TrelloBoard("1", "board1",testList);
        List<TrelloBoard> mappedBoards = trelloMapper.mapToBoards(boardDtos);
        //Then
        Assert.assertEquals(mappedBoards.get(0).getName(),testBoard.getName());
        Assert.assertEquals(mappedBoards.get(0).getLists().get(0).getName(),testBoard.getLists().get(0).getName());
        Assert.assertEquals(mappedBoards.get(0).getClass(),testBoard.getClass());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloBoard> testBoards = trelloMapper.mapToBoards(boardDtos);
        //When
        List<TrelloBoardDto> testBoardsDtos = trelloMapper.mapToBoardsDto(testBoards);
        //Then
        Assert.assertEquals(boardDtos.get(0).getName(),testBoardsDtos.get(0).getName());
        Assert.assertEquals(boardDtos.get(0).getLists().get(0).getName(),testBoardsDtos.get(0).getLists().get(0).getName());
        Assert.assertEquals(boardDtos.get(0).getClass(),testBoardsDtos.get(0).getClass());
    }

    @Test
    public void testMapToList() {
        //Given
        //When
        List<TrelloList> testList = new ArrayList<>();
        testList.add(new TrelloList("1","list1",false));
        testList.add(new TrelloList("2","list2",false));
        List<TrelloList> mappedList = trelloMapper.mapToList(listDtos1);
        //Then
        Assert.assertEquals(mappedList.get(0).getName(),testList.get(0).getName());
        Assert.assertEquals(mappedList.get(0).getClass(),testList.get(0).getClass());
    }

    @Test
    public void testMapToListDto() {
        //Given
        //When
        List<TrelloList> testList = trelloMapper.mapToList(listDtos2);
        List<TrelloListDto> mappedListDto = trelloMapper.mapToListDto(testList);
        //Then
        Assert.assertEquals(mappedListDto.get(0).getName(),listDtos2.get(0).getName());
        Assert.assertEquals(mappedListDto.get(0).getClass(),listDtos2.get(0).getClass());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard testCard = new TrelloCard("card1","c","1","1");
        //When
        TrelloCardDto testCardDto = new TrelloCardDto("card1","c1","1","1");
        TrelloCardDto mapppedCardDto = trelloMapper.mapToCardDto(testCard);
        //Then
        Assert.assertEquals(mapppedCardDto.getName(),testCardDto.getName());
        Assert.assertEquals(mapppedCardDto.getClass(),testCardDto.getClass());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto testCardDto = new TrelloCardDto("card1","c1","1","1");
        //When
        TrelloCard testCard = new TrelloCard("card1","c1","1","1");
        TrelloCard mappedCard = trelloMapper.mapToCard(testCardDto);
        //Then
        Assert.assertEquals(mappedCard.getName(),testCard.getName());
        Assert.assertEquals(mappedCard.getClass(),testCard.getClass());
    }



}
