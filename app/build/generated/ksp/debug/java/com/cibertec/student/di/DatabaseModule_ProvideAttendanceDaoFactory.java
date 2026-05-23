package com.cibertec.student.di;

import com.cibertec.student.data.local.dao.AttendanceDao;
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
public final class DatabaseModule_ProvideAttendanceDaoFactory implements Factory<AttendanceDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideAttendanceDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AttendanceDao get() {
    return provideAttendanceDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAttendanceDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAttendanceDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideAttendanceDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAttendanceDaoFactory(dbProvider);
  }

  public static AttendanceDao provideAttendanceDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAttendanceDao(db));
  }
}
