package com.cibertec.student.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.cibertec.student.data.local.converter.Converters;
import com.cibertec.student.data.local.entity.CourseEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CourseDao_Impl implements CourseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CourseEntity> __insertionAdapterOfCourseEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<CourseEntity> __deletionAdapterOfCourseEntity;

  private final EntityDeletionOrUpdateAdapter<CourseEntity> __updateAdapterOfCourseEntity;

  public CourseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourseEntity = new EntityInsertionAdapter<CourseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `courses` (`id`,`userId`,`name`,`teacher`,`classroom`,`startTime`,`endTime`,`days`,`credits`,`color`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CourseEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getTeacher());
        statement.bindString(5, entity.getClassroom());
        statement.bindString(6, entity.getStartTime());
        statement.bindString(7, entity.getEndTime());
        final String _tmp = __converters.fromIntList(entity.getDays());
        statement.bindString(8, _tmp);
        statement.bindLong(9, entity.getCredits());
        statement.bindString(10, entity.getColor());
        statement.bindLong(11, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfCourseEntity = new EntityDeletionOrUpdateAdapter<CourseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `courses` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CourseEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCourseEntity = new EntityDeletionOrUpdateAdapter<CourseEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `courses` SET `id` = ?,`userId` = ?,`name` = ?,`teacher` = ?,`classroom` = ?,`startTime` = ?,`endTime` = ?,`days` = ?,`credits` = ?,`color` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CourseEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getTeacher());
        statement.bindString(5, entity.getClassroom());
        statement.bindString(6, entity.getStartTime());
        statement.bindString(7, entity.getEndTime());
        final String _tmp = __converters.fromIntList(entity.getDays());
        statement.bindString(8, _tmp);
        statement.bindLong(9, entity.getCredits());
        statement.bindString(10, entity.getColor());
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getId());
      }
    };
  }

  @Override
  public Object insertCourse(final CourseEntity course,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCourseEntity.insertAndReturnId(course);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCourse(final CourseEntity course,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCourseEntity.handle(course);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCourse(final CourseEntity course,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCourseEntity.handle(course);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CourseEntity>> getCourses(final String userId) {
    final String _sql = "SELECT * FROM courses WHERE userId = ? ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"courses"}, new Callable<List<CourseEntity>>() {
      @Override
      @NonNull
      public List<CourseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
          final int _cursorIndexOfClassroom = CursorUtil.getColumnIndexOrThrow(_cursor, "classroom");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDays = CursorUtil.getColumnIndexOrThrow(_cursor, "days");
          final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CourseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTeacher;
            _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
            final String _tmpClassroom;
            _tmpClassroom = _cursor.getString(_cursorIndexOfClassroom);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final List<Integer> _tmpDays;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDays);
            _tmpDays = __converters.toIntList(_tmp);
            final int _tmpCredits;
            _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
            final String _tmpColor;
            _tmpColor = _cursor.getString(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CourseEntity(_tmpId,_tmpUserId,_tmpName,_tmpTeacher,_tmpClassroom,_tmpStartTime,_tmpEndTime,_tmpDays,_tmpCredits,_tmpColor,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<CourseEntity>> getCoursesForDay(final String userId, final String day) {
    final String _sql = "SELECT * FROM courses WHERE userId = ? AND days LIKE '%' || ? || '%' ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, day);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"courses"}, new Callable<List<CourseEntity>>() {
      @Override
      @NonNull
      public List<CourseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
          final int _cursorIndexOfClassroom = CursorUtil.getColumnIndexOrThrow(_cursor, "classroom");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDays = CursorUtil.getColumnIndexOrThrow(_cursor, "days");
          final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CourseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTeacher;
            _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
            final String _tmpClassroom;
            _tmpClassroom = _cursor.getString(_cursorIndexOfClassroom);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final List<Integer> _tmpDays;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDays);
            _tmpDays = __converters.toIntList(_tmp);
            final int _tmpCredits;
            _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
            final String _tmpColor;
            _tmpColor = _cursor.getString(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CourseEntity(_tmpId,_tmpUserId,_tmpName,_tmpTeacher,_tmpClassroom,_tmpStartTime,_tmpEndTime,_tmpDays,_tmpCredits,_tmpColor,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCourseById(final long id, final Continuation<? super CourseEntity> $completion) {
    final String _sql = "SELECT * FROM courses WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CourseEntity>() {
      @Override
      @Nullable
      public CourseEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
          final int _cursorIndexOfClassroom = CursorUtil.getColumnIndexOrThrow(_cursor, "classroom");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDays = CursorUtil.getColumnIndexOrThrow(_cursor, "days");
          final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final CourseEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTeacher;
            _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
            final String _tmpClassroom;
            _tmpClassroom = _cursor.getString(_cursorIndexOfClassroom);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final List<Integer> _tmpDays;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDays);
            _tmpDays = __converters.toIntList(_tmp);
            final int _tmpCredits;
            _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
            final String _tmpColor;
            _tmpColor = _cursor.getString(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new CourseEntity(_tmpId,_tmpUserId,_tmpName,_tmpTeacher,_tmpClassroom,_tmpStartTime,_tmpEndTime,_tmpDays,_tmpCredits,_tmpColor,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCourseCount(final String userId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM courses WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllCoursesList(final String userId,
      final Continuation<? super List<CourseEntity>> $completion) {
    final String _sql = "SELECT * FROM courses WHERE userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CourseEntity>>() {
      @Override
      @NonNull
      public List<CourseEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTeacher = CursorUtil.getColumnIndexOrThrow(_cursor, "teacher");
          final int _cursorIndexOfClassroom = CursorUtil.getColumnIndexOrThrow(_cursor, "classroom");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDays = CursorUtil.getColumnIndexOrThrow(_cursor, "days");
          final int _cursorIndexOfCredits = CursorUtil.getColumnIndexOrThrow(_cursor, "credits");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<CourseEntity> _result = new ArrayList<CourseEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CourseEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTeacher;
            _tmpTeacher = _cursor.getString(_cursorIndexOfTeacher);
            final String _tmpClassroom;
            _tmpClassroom = _cursor.getString(_cursorIndexOfClassroom);
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            final List<Integer> _tmpDays;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDays);
            _tmpDays = __converters.toIntList(_tmp);
            final int _tmpCredits;
            _tmpCredits = _cursor.getInt(_cursorIndexOfCredits);
            final String _tmpColor;
            _tmpColor = _cursor.getString(_cursorIndexOfColor);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new CourseEntity(_tmpId,_tmpUserId,_tmpName,_tmpTeacher,_tmpClassroom,_tmpStartTime,_tmpEndTime,_tmpDays,_tmpCredits,_tmpColor,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
