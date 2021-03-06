package com.baogetv.app.db.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VIDEO_ENTITY".
*/
public class VideoEntityDao extends AbstractDao<VideoEntity, Long> {

    public static final String TABLENAME = "VIDEO_ENTITY";

    /**
     * Properties of entity VideoEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property VideoName = new Property(1, String.class, "videoName", false, "VIDEONAME");
        public final static Property Cover = new Property(2, String.class, "cover", false, "COVER_LOCALE");
    }


    public VideoEntityDao(DaoConfig config) {
        super(config);
    }
    
    public VideoEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VIDEO_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"VIDEONAME\" TEXT," + // 1: videoName
                "\"COVER_LOCALE\" TEXT);"); // 2: cover
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VIDEO_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, VideoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String videoName = entity.getVideoName();
        if (videoName != null) {
            stmt.bindString(2, videoName);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(3, cover);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, VideoEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String videoName = entity.getVideoName();
        if (videoName != null) {
            stmt.bindString(2, videoName);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(3, cover);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public VideoEntity readEntity(Cursor cursor, int offset) {
        VideoEntity entity = new VideoEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // videoName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // cover
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, VideoEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setVideoName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCover(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(VideoEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(VideoEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(VideoEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
