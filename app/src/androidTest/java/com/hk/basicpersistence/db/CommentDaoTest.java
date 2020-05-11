package com.hk.basicpersistence.db;


import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.hk.basicpersistence.LiveDataTestUtil;
import com.hk.basicpersistence.db.dao.CommentDao;
import com.hk.basicpersistence.db.dao.ProductDao;
import com.hk.basicpersistence.db.entity.CommentEntity;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.hk.basicpersistence.db.TestData.COMMENTS;
import static com.hk.basicpersistence.db.TestData.COMMENT_ENTITY;
import static com.hk.basicpersistence.db.TestData.PRODUCTS;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class CommentDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private AppDatabase mAppDatabase;

    private CommentDao mCommentDao;

    private ProductDao mProductDao;


    @Before
    public void initDB() throws Exception {
        mAppDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        mCommentDao = mAppDatabase.commentDao();
        mProductDao = mAppDatabase.productDao();
    }

    @Test
    public void getCommentsWhenNoCommentInserted() throws InterruptedException {
        List<CommentEntity> comments = LiveDataTestUtil.getValue(mCommentDao.loadComments(COMMENT_ENTITY.getProductId()));
        assertTrue(comments.isEmpty());
    }

    @Test
    public void getCommentsAfterInserted() throws InterruptedException {
        mProductDao.insertAll(PRODUCTS);
        mCommentDao.insertAll(COMMENTS);

        List<CommentEntity> comments = LiveDataTestUtil.getValue(mCommentDao.loadComments(COMMENT_ENTITY.getProductId()));

        assertThat(comments.size(), is(1));
    }

    @Test
    public void canInsertCommentWithoutProduct() throws InterruptedException {
        mCommentDao.insertAll(COMMENTS);
        try {
            mCommentDao.insertAll(COMMENTS);
            fail("SQLiteConstraintException expected");
        } catch (SQLiteConstraintException ignored) {

        }
    }


    @Test
    public void getCommentByProductId() throws InterruptedException {
        mProductDao.insertAll(PRODUCTS);
        mCommentDao.insertAll(COMMENTS);

        List<CommentEntity> comments = LiveDataTestUtil.getValue(mCommentDao.loadComments(COMMENT_ENTITY.getProductId()));

        assertThat(comments.size(), is(1));
    }


    @After
    public void closeDb() throws Exception {
        mAppDatabase.close();
    }

}
