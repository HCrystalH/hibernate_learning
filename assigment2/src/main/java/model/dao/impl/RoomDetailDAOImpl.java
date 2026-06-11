package model.dao.impl;

import model.entities.RoomDetail;
import model.dao.RoomDetailDAO;

public class RoomDetailDAOImpl extends AbstractIDAOImpl<RoomDetail,Integer> implements RoomDetailDAO {
    public RoomDetailDAOImpl(Class<RoomDetail> persistentClass) {
        super(persistentClass);
    }
}
