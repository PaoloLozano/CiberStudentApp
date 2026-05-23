package com.cibertec.student.data.repository;

import com.cibertec.student.data.local.dao.AttendanceDao;
import com.cibertec.student.data.local.dao.CourseDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AttendanceRepositoryImpl_Factory implements Factory<AttendanceRepositoryImpl> {
  private final Provider<AttendanceDao> attendanceDaoProvider;

  private final Provider<CourseDao> courseDaoProvider;

  public AttendanceRepositoryImpl_Factory(Provider<AttendanceDao> attendanceDaoProvider,
      Provider<CourseDao> courseDaoProvider) {
    this.attendanceDaoProvider = attendanceDaoProvider;
    this.courseDaoProvider = courseDaoProvider;
  }

  @Override
  public AttendanceRepositoryImpl get() {
    return newInstance(attendanceDaoProvider.get(), courseDaoProvider.get());
  }

  public static AttendanceRepositoryImpl_Factory create(
      javax.inject.Provider<AttendanceDao> attendanceDaoProvider,
      javax.inject.Provider<CourseDao> courseDaoProvider) {
    return new AttendanceRepositoryImpl_Factory(Providers.asDaggerProvider(attendanceDaoProvider), Providers.asDaggerProvider(courseDaoProvider));
  }

  public static AttendanceRepositoryImpl_Factory create(
      Provider<AttendanceDao> attendanceDaoProvider, Provider<CourseDao> courseDaoProvider) {
    return new AttendanceRepositoryImpl_Factory(attendanceDaoProvider, courseDaoProvider);
  }

  public static AttendanceRepositoryImpl newInstance(AttendanceDao attendanceDao,
      CourseDao courseDao) {
    return new AttendanceRepositoryImpl(attendanceDao, courseDao);
  }
}
