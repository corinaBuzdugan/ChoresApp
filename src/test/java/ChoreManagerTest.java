import org.example.ChoreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChoreManagerTest {
    private ChoreManager choreManager;

    @BeforeEach
    public void setUp() {
        choreManager = new ChoreManager();
    }

    @Test
    public void testAddCustomChore() {
        // Add a custom chore
        String customChoreName = "Custom Chore";
        int customChorePoints = 20;
        choreManager.addCustomChore(customChoreName, customChorePoints);

        // Check if the custom chore is available in the chore manager
        HashMap<String, Integer> availableChores = choreManager.getAvailableChores();
        assertEquals(true, availableChores.containsKey(customChoreName));
        assertEquals(customChorePoints, availableChores.get(customChoreName));
    }

    @Test
    public void testGetChoreDescription() {
        // Test getting description for an existing chore
        String choreName = "Bed";
        String expectedDescription = "Make your bed in the morning";
        String actualDescription = choreManager.getChoreDescription(choreName);
        assertEquals(expectedDescription, actualDescription);

        // Test getting description for a non-existing chore
        String nonExistingChore = "Non-Existing Chore";
        String defaultDescription = "No description available";
        String nonExistingDescription = choreManager.getChoreDescription(nonExistingChore);
        assertEquals(defaultDescription, nonExistingDescription);
    }
}
