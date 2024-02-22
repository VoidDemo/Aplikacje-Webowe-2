package com.jsfweb.room;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import hotel.jsf.dao.RoomDAO;
import hotel.jsf.entity.Pokoje;
import jakarta.ejb.EJB;

import java.util.List;
import java.util.Map;

public class LazyRoomDataModel extends LazyDataModel<Pokoje> {
    private static final long serialVersionUID = 1L;

    @EJB
    private RoomDAO roomDAO;
    private String sortOption;

    public LazyRoomDataModel(RoomDAO roomDAO, String sortOption) {
        this.roomDAO = roomDAO;
        this.sortOption = sortOption;
    }

    @Override
    public List<Pokoje> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
    	String sortField = null;
        String sortOrder = null; 

        if (!sortBy.isEmpty()) {
            // Przykład dla jednego pola sortowania
            Map.Entry<String, SortMeta> sortEntry = sortBy.entrySet().iterator().next();
            sortField = sortEntry.getKey();
            sortOrder = sortEntry.getValue().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC";
        }
        
        List<Pokoje> rooms = roomDAO.findRoomsPaginated(first, pageSize, sortField, sortOrder);
        int rowCount = roomDAO.countFilteredRooms();
        this.setRowCount(rowCount);
        
        return rooms;
    }
    

    @Override
    public int count(Map<String, FilterMeta> filters) {
        return roomDAO.countFilteredRooms();
    }
}
