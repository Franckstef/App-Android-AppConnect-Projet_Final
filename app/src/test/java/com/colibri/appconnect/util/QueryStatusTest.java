package com.colibri.appconnect.util;


import org.junit.Test;
import static org.junit.Assert.*;

public class QueryStatusTest {

    private final String sData = "This is data";
    private final String sMessage = "This is message";

    public QueryStatus<String> getSuccess(){
        return new QueryStatus.Success<>(sData);
    }

    public QueryStatus<String> getEmptySuccess(){
        return new QueryStatus.Success<>();
    }

    public QueryStatus<String> getErrorWithData(){
        return new QueryStatus.Error<>(sMessage,sData);
    }

    public QueryStatus<String> getError(){
        return new QueryStatus.Error<>(sMessage);
    }

    public QueryStatus<String> getLoading(){
        return new QueryStatus.Loading<>();
    }

    @Test
    public void testSuccess() {
        assertTrue(getSuccess() instanceof QueryStatus.Success);
        assertTrue(getEmptySuccess() instanceof QueryStatus.Success);
        assertFalse(getEmptySuccess() instanceof QueryStatus.Error);
        assertFalse(getEmptySuccess() instanceof QueryStatus.Loading);
    }

    @Test
    public void testError() {
        assertTrue(getError() instanceof QueryStatus.Error);
        assertTrue(getErrorWithData() instanceof QueryStatus.Error);
        assertFalse(getError() instanceof QueryStatus.Success);
        assertFalse(getError() instanceof QueryStatus.Loading);
    }

    @Test
    public void testLoading() {
        assertTrue(getLoading() instanceof QueryStatus.Loading);
        assertFalse(getLoading() instanceof QueryStatus.Success);
        assertFalse(getLoading() instanceof QueryStatus.Error);
    }

    @Test
    public void testGetData(){
        assertEquals(sData,getSuccess().getData());
        assertNull(getEmptySuccess().getData());
        assertEquals(sData,getErrorWithData().getData());
        assertNull(getError().getData());
        assertNull(getLoading().getData());
    }

    @Test
    public void testGetMessage(){
        assertNull(getSuccess().getMessage());
        assertNull(getEmptySuccess().getMessage());
        assertNull(getLoading().getMessage());
        assertEquals(sMessage,getErrorWithData().getMessage());
        assertEquals(sMessage,getErrorWithData().getMessage());
    }

}