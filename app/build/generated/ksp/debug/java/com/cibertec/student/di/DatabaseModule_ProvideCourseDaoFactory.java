package com.cibertec.student.di;

import com.cibertec.student.data.local.dao.CourseDao;
import com.cibertec.student.data.local.database.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DatabaseModule_ProvideCourseDaoFactory implements Factory<CourseDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideCourseDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CourseDao get() {
    return provideCourseDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCourseDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCourseDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideCourseDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCourseDaoFactory(dbProvider);
  }

  public static CourseDao provideCourseDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCourseDao(db));
  }
}
