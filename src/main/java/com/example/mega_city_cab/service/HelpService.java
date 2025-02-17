package com.example.mega_city_cab.service;

import com.example.mega_city_cab.dao.HelpDAO;
import com.example.mega_city_cab.model.Help;

import java.sql.SQLException;
import java.util.List;

public class HelpService {
    private HelpDAO helpDAO;

    public HelpService() {
        this.helpDAO = new HelpDAO();
    }

    public void addGuideline(Help help) throws SQLException {
        helpDAO.addGuideline(help);
    }

    public Help getGuideline(int helpID) throws SQLException {
        return helpDAO.getGuideline(helpID);
    }

    public List<Help> getAllGuidelines() throws SQLException {
        return helpDAO.getAllGuidelines();
    }

    public void updateGuideline(Help help) throws SQLException {
        helpDAO.updateGuideline(help);
    }

    public void deleteGuideline(int helpID) throws SQLException {
        helpDAO.deleteGuideline(helpID);
    }
}