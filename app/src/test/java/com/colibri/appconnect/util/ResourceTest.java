package com.colibri.appconnect.util;


import org.junit.Test;
import static org.junit.Assert.*;

public class ResourceTest {

    private final String sData = "This is data";
    private final String sMessage = "This is message";

    public Resource<String> getSuccess(){
        return new Resource.Success<>(sData);
    }

    public Resource<String> getEmptySuccess(){
        return new Resource.Success<>();
    }

    public Resource<String> getErrorWithData(){
        return new Resource.Error<>(sMessage,sData);
    }

    public Resource<String> getError(){
        return new Resource.Error<>(sMessage);
    }

    public Resource<String> getLoading(){
        return new Resource.Loading<>();
    }

    @Test
    public void testSuccess() {
        assertTrue(getSuccess() instanceof Resource.Success);
        assertTrue(getEmptySuccess() instanceof Resource.Success);
        assertFalse(getEmptySuccess() instanceof Resource.Error);
        assertFalse(getEmptySuccess() instanceof Resource.Loading);
    }

    @Test
    public void testError() {
        assertTrue(getError() instanceof Resource.Error);
        assertTrue(getErrorWithData() instanceof Resource.Error);
        assertFalse(getError() instanceof Resource.Success);
        assertFalse(getError() instanceof Resource.Loading);
    }

    @Test
    public void testLoading() {
        assertTrue(getLoading() instanceof Resource.Loading);
        assertFalse(getLoading() instanceof Resource.Success);
        assertFalse(getLoading() instanceof Resource.Error);
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