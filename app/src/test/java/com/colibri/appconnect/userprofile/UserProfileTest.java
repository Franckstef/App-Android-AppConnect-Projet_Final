package com.colibri.appconnect.userprofile;



import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class UserProfileTest {
    private UserProfile up;
    private String userId;
    private String displayName;
    private String primaryEmail;
    private String primaryPhoneNumber;
    private String avatar;
    private String title;
    private String location;
    private String newStringValue;

    @Before
    public void setUp() {

        userId = "-1";
        displayName = "Philippe Allard-Rousse";
        primaryEmail = "philrousse@gmail.com";
        primaryPhoneNumber = "(514) 555-5555";
        avatar = "";
        title="Contre-Maitre";
        location = "Chantier du Chum";
        up = getUserProfile();
        newStringValue = "NewStringValue";
    }

    private UserProfile getUserProfile(){
        return new UserProfile(
                userId,
                displayName,
                primaryEmail,
                primaryPhoneNumber,
                avatar,
                title,
                location
        );
    }

    @Test
    public void testMockUserProfile() {
        assertNotNull(UserProfile.MockUserProfile());
    }

    @Test
    public void testGetUserId() {
        assertEquals(userId,up.getUserId());
    }

    @Test
    public void testGetDisplayName() {
        assertEquals(displayName,up.getDisplayName());
    }

    @Test
    public void testSetDisplayName() {
        up.setDisplayName(newStringValue);
        assertEquals(newStringValue,up.getDisplayName());
    }


    @Test
    public void testWithDisplayName() {

        UserProfile newProfile = up.withDisplayName(newStringValue);
        assertEquals(newStringValue,newProfile.getDisplayName());
        assertEquals(displayName,up.getDisplayName());
        assertNotEquals(up,newProfile);

    }

    @Test
    public void testGetPrimaryEmail() {
        assertEquals(primaryEmail,up.getPrimaryEmail());
    }

    @Test
    public void testSetPrimaryEmail() {
        up.setPrimaryEmail(newStringValue);
        assertEquals(newStringValue,up.getPrimaryEmail());
    }

    ///////////////////////////////////////
    @Test
    public void testWithPrimaryEmail() {
        UserProfile newProfile = up.withPrimaryEmail(newStringValue);
        assertEquals(newStringValue,newProfile.getPrimaryEmail());
        assertEquals(primaryEmail,up.getPrimaryEmail());
        assertNotEquals(up,newProfile);
    }

    @Test
    public void testGetPrimaryPhoneNumber() {
        assertEquals(primaryPhoneNumber,up.getPrimaryPhoneNumber());
    }

    @Test
    public void testSetPrimaryPhoneNumber() {
        up.setPrimaryPhoneNumber(newStringValue);
        assertEquals(newStringValue,up.getPrimaryPhoneNumber());
    }

    @Test
    public void testWithPrimaryPhoneNumber() {
        UserProfile newProfile = up.withPrimaryPhoneNumber(newStringValue);
        assertEquals(newStringValue,newProfile.getPrimaryPhoneNumber());
        assertEquals(primaryPhoneNumber,up.getPrimaryPhoneNumber());
        assertNotEquals(up,newProfile);
    }

    @Test
    public void testGetAvatar() {
        assertEquals(avatar,up.getAvatar());
    }

    @Test
    public void testSetAvatar() {
        up.setAvatar(newStringValue);
        assertEquals(newStringValue,up.getAvatar());
    }

    @Test
    public void testWithAvatar() {
        UserProfile newProfile = up.withAvatar(newStringValue);
        assertEquals(newStringValue,newProfile.getAvatar());
        assertEquals(avatar,up.getAvatar());
        assertNotEquals(up,newProfile);
    }

    @Test
    public void testGetTitle() {
        assertEquals(title,up.getTitle());
    }

    @Test
    public void testSetTitle() {
        up.setTitle(newStringValue);
        assertEquals(newStringValue,up.getTitle());
    }

    @Test
    public void testWithTitle() {
        UserProfile newProfile = up.withTitle(newStringValue);
        assertEquals(newStringValue,newProfile.getTitle());
        assertEquals(title,up.getTitle());
        assertNotEquals(up,newProfile);
    }

    @Test
    public void testGetLocation() {
        assertEquals(location,up.getLocation());
    }

    @Test
    public void testSetLocation() {
        up.setLocation(newStringValue);
        assertEquals(newStringValue,up.getLocation());
    }

    @Test
    public void testWithLocation() {
        UserProfile newProfile = up.withLocation(newStringValue);
        assertEquals(newStringValue,newProfile.getLocation());
        assertEquals(location,up.getLocation());
        assertNotEquals(up,newProfile);
    }


    @Test
    public void testTestToString() {
        assertNotNull(up.toString());
        assertFalse(up.toString().isEmpty());
    }

    @Test
    public void testTestHashCode() {
        assertEquals(up.hashCode(),getUserProfile().hashCode());
    }

    @Test
    public void testTestEquals() {
        assertEquals(up,getUserProfile());
        assertNotEquals(up,getUserProfile().withTitle("SomethingElse"));
    }
}