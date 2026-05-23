package com.cibertec.student.data.repository;

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
public final class CourseRepositoryImpl_Factory implements Factory<CourseRepositoryImpl> {
  private final Provider<CourseDao> courseDaoProvider;

  public CourseRepositoryImpl_Factory(Provider<CourseDao> courseDaoProvider) {
    this.courseDaoProvider = courseDaoProvider;
  }

  @Override
  public CourseRepositoryImpl get() {
    return newInstance(courseDaoProvider.get());
  }

  public static CourseRepositoryImpl_Factory create(
      javax.inject.Provider<CourseDao> courseDaoProvider) {
    return new CourseRepositoryImpl_Factory(Providers.asDaggerProvider(courseDaoProvider));
  }

  public static CourseRepositoryImpl_Factory create(Provider<CourseDao> courseDaoProvider) {
    return new CourseRepositoryImpl_Factory(courseDaoProvider);
  }

  public static CourseRepositoryImpl newInstance(CourseDao courseDao) {
    return new CourseRepositoryImpl(courseDao);
  }
}
