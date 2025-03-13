package com.example.mega_city_cab.service;

import com.example.mega_city_cab.dao.HelpDAO;
import com.example.mega_city_cab.model.Help;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HelpServiceTest {

    private HelpService helpService;
    private HelpDAO helpDAO;
    private Help help;

    @BeforeEach
    void setUp() {
        // Mock HelpDAO implementation
        helpDAO = new HelpDAO() {
            private List<Help> guidelines = new ArrayList<>();

            @Override
            public void addGuideline(Help help) throws SQLException {
                guidelines.add(help);
            }

            @Override
            public void updateGuideline(Help help) throws SQLException {
                for (int i = 0; i < guidelines.size(); i++) {
                    if (guidelines.get(i).getHelpID() == help.getHelpID()) {
                        guidelines.set(i, help);
                        break;
                    }
                }
            }

            @Override
            public void deleteGuideline(int helpID) throws SQLException {
                guidelines.removeIf(h -> h.getHelpID() == helpID);
            }

            @Override
            public List<Help> getAllGuidelines() throws SQLException {
                return new ArrayList<>(guidelines);
            }

            @Override
            public Help getHelp(int helpID) throws SQLException {
                return guidelines.stream()
                        .filter(h -> h.getHelpID() == helpID)
                        .findFirst()
                        .orElse(null);
            }
        };

        // Initialize HelpService and inject the mock HelpDAO
        helpService = new HelpService();
        helpService.setHelpDAO(helpDAO);

        // Create a sample help guideline for testing
        help = new Help();
        help.setHelpID(1);
        help.setGuideline("Test Guideline");
    }

    @Test
    void testAddGuideline() throws SQLException {
        // Add the guideline to the mock database
        helpService.addGuideline(help);

        // Retrieve the guideline from the mock database
        Help retrievedHelp = helpService.getHelp(1);

        // Assert that the guideline was added successfully
        assertNotNull(retrievedHelp);
        assertEquals(1, retrievedHelp.getHelpID());
        assertEquals("Test Guideline", retrievedHelp.getGuideline());
    }

    @Test
    void testUpdateGuideline() throws SQLException {
        // Add the guideline to the mock database
        helpService.addGuideline(help);

        // Update the guideline's text
        help.setGuideline("Updated Guideline");
        helpService.updateGuideline(help);

        // Retrieve the updated guideline from the mock database
        Help updatedHelp = helpService.getHelp(1);

        // Assert that the guideline was updated successfully
        assertNotNull(updatedHelp);
        assertEquals("Updated Guideline", updatedHelp.getGuideline());
    }

    @Test
    void testDeleteGuideline() throws SQLException {
        // Add the guideline to the mock database
        helpService.addGuideline(help);

        // Delete the guideline
        helpService.deleteGuideline(1);

        // Attempt to retrieve the deleted guideline
        Help deletedHelp = helpService.getHelp(1);

        // Assert that the guideline was deleted successfully
        assertNull(deletedHelp);
    }

    @Test
    void testGetAllGuidelines() throws SQLException {
        // Add the guideline to the mock database
        helpService.addGuideline(help);

        // Retrieve all guidelines from the mock database
        List<Help> retrievedGuidelines = helpService.getAllGuidelines();

        // Assert that the list contains the added guideline
        assertNotNull(retrievedGuidelines);
        assertEquals(1, retrievedGuidelines.size());
        assertEquals("Test Guideline", retrievedGuidelines.get(0).getGuideline());
    }

    @Test
    void testGetHelp() throws SQLException {
        // Add the guideline to the mock database
        helpService.addGuideline(help);

        // Retrieve the guideline by its ID
        Help retrievedHelp = helpService.getHelp(1);

        // Assert that the guideline was retrieved successfully
        assertNotNull(retrievedHelp);
        assertEquals(1, retrievedHelp.getHelpID());
        assertEquals("Test Guideline", retrievedHelp.getGuideline());
    }
}