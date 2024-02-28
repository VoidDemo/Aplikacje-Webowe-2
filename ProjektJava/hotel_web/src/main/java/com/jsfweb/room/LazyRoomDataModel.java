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

    private RoomList roomlist;	 
    
    private RoomDAO roomDAO;
    private String sortOption;

    public LazyRoomDataModel(RoomDAO roomDAO, String sortOption, RoomList roomlist) {
        this.roomDAO = roomDAO;
        this.sortOption = sortOption;
        this.roomlist = roomlist;
       
    }

    @Override
    public List<Pokoje> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
        String sortOrder = sortOption; 
        
        if (!sortBy.isEmpty()) {
            Map.Entry<String, SortMeta> sortEntry = sortBy.entrySet().iterator().next();
            sortOrder = sortEntry.getValue().getOrder() == SortOrder.ASCENDING ? "ASC" : "DESC";
        }
        
        // Wywołanie metody findRoomsPaginated z ustalonym kierunkiem sortowania
        List<Pokoje> rooms = roomDAO.findRoomsPaginated(first, pageSize, sortOrder,  roomlist.getTypPokoju(), roomlist.getLiczbaOsob(), roomlist.getKuchnia(), roomlist.getKlimatyzacja(), roomlist.getTelewizor());
        int rowCount = roomDAO.countFilteredRooms( roomlist.getTypPokoju(), roomlist.getLiczbaOsob(), roomlist.getKuchnia(), roomlist.getKlimatyzacja(), roomlist.getTelewizor());
        this.setRowCount(rowCount);
        
        return rooms;
    }
    

    @Override
    public int count(Map<String, FilterMeta> filters) {
        return roomDAO.countFilteredRooms(roomlist.getTypPokoju(), roomlist.getLiczbaOsob(), roomlist.getKuchnia(), roomlist.getKlimatyzacja(), roomlist.getTelewizor());
    }
}
