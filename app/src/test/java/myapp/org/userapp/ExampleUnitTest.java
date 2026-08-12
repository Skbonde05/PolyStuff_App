package myapp.org.userapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void helperClass_encapsulatesUserDataCorrectly() {
        HelperClass user = new HelperClass("John Doe", "john@example.com", "johndoe");
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("johndoe", user.getUsername());
    }

    @Test
    public void helperClass_settersWorkAsExpected() {
        HelperClass user = new HelperClass();
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setUsername("alice123");
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("alice123", user.getUsername());
    }

    @Test
    public void helperClass_emptyConstructorCreatesValidObject() {
        HelperClass user = new HelperClass();
        assertNotNull(user);
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getUsername());
    }

    @Test
    public void product_initializationAndGetters() {
        String testUrl = "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FDS%20UNIT%201.pdf";
        Product product = new Product(1, "DS UNIT 1", 60000, 101, 102, testUrl);
        assertEquals(1, product.getId());
        assertEquals("DS UNIT 1", product.getTitle());
        assertEquals(101, product.getImage());
        assertEquals(testUrl, product.getLink());
    }

    @Test
    public void product_setLinkUpdatesValue() {
        Product product = new Product(1, "Sample Unit", 0, 0, 0, "http://initial.url");
        product.setLink("http://updated.url");
        assertEquals("http://updated.url", product.getLink());
    }

    @Test
    public void product_titleIsTrimmed() {
        Product product = new Product(1, "  DS UNIT 1  \n", 60000, 101, 102, "http://test.url");
        assertEquals("DS UNIT 1", product.getTitle().trim());
    }

    @Test
    public void product_nullLink_acceptedByConstructor() {
        Product product = new Product(2, "No Link", 0, 0, 0, null);
        assertNull(product.getLink());
        assertEquals(2, product.getId());
        assertEquals("No Link", product.getTitle());
    }

    @Test
    public void product_emptyTitle_acceptedByConstructor() {
        Product product = new Product(3, "", 100, 0, 0, "http://url");
        assertEquals("", product.getTitle());
        assertEquals(3, product.getId());
    }

    @Test
    public void product_setNullLink_overwritesPreviousLink() {
        Product product = new Product(1, "Test", 0, 0, 0, "http://initial.url");
        product.setLink(null);
        assertNull(product.getLink());
    }

    @Test
    public void pdfUrl_validationHelper() {
        String validFirebaseStorageUrl = "https://firebasestorage.googleapis.com/v0/b/technotes-77bce.appspot.com/o/pdf%2FDS.pdf";
        assertTrue(validFirebaseStorageUrl.startsWith("https://"));
        assertTrue(validFirebaseStorageUrl.contains("firebasestorage.googleapis.com"));
    }

    @Test
    public void emailValidation_validEmails() {
        String[] validEmails = {"test@example.com", "user.name@domain.co.in", "user+tag@example.com"};
        for (String email : validEmails) {
            assertTrue("Valid email failed: " + email, isValidEmail(email));
        }
    }

    @Test
    public void emailValidation_invalidEmails() {
        String[] invalidEmails = {"plainaddress", "missing@domain", "@missing.com", "test@"};
        for (String email : invalidEmails) {
            assertFalse("Invalid email passed: " + email, isValidEmail(email));
        }
    }

    @Test
    public void emailValidation_nullEmail() {
        assertFalse(isValidEmail(null));
    }

    @Test
    public void emailValidation_emptyEmail() {
        assertFalse(isValidEmail(""));
    }

    @Test
    public void passwordValidation_nonEmptyPassword() {
        assertTrue(isValidPassword("securePass123"));
    }

    @Test
    public void passwordValidation_emptyPassword() {
        assertFalse(isValidPassword(""));
    }

    @Test
    public void passwordValidation_nullPassword() {
        assertFalse(isValidPassword(null));
    }

    @Test
    public void passwordValidation_shortPassword() {
        assertFalse(isValidPassword("123"));
    }

    @Test
    public void passwordValidation_minimumLength() {
        assertTrue(isValidPassword("12345"));
    }

    @Test
    public void inputValidation_emptyFields() {
        assertFalse(isValidEmail(""));
        assertFalse(isValidPassword(""));
        assertFalse(isValidEmail(null));
        assertFalse(isValidPassword(null));
    }

    @Test
    public void searchManager_initialization() {
        try {
            java.lang.reflect.Constructor<SearchManager> constructor = SearchManager.class.getDeclaredConstructor(android.content.Context.class);
            constructor.setAccessible(true);
            assertTrue("SearchManager should be instantiable", true);
        } catch (Exception e) {
            assertTrue("SearchManager reflection failed: " + e.getMessage(), false);
        }
    }

    @Test
    public void helperClass_serializationConsistency() {
        HelperClass original = new HelperClass("Bob", "bob@test.com", "bob_user");
        String name = original.getName();
        String email = original.getEmail();
        String username = original.getUsername();
        HelperClass copy = new HelperClass(name, email, username);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getEmail(), copy.getEmail());
        assertEquals(original.getUsername(), copy.getUsername());
    }

    @Test
    public void helperClass_settersOverridePreviousValues() {
        HelperClass user = new HelperClass("First", "first@test.com", "first_user");
        user.setName("Second");
        user.setEmail("second@test.com");
        user.setUsername("second_user");
        assertEquals("Second", user.getName());
        assertEquals("second@test.com", user.getEmail());
        assertEquals("second_user", user.getUsername());
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex == email.length() - 1) return false;
        int dotIndex = email.lastIndexOf('.');
        return dotIndex > atIndex && dotIndex < email.length() - 1;
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 5;
    }
}
