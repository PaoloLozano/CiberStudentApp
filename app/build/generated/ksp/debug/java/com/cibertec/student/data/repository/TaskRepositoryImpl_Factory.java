package com.cibertec.student.data.repository;

import com.cibertec.student.data.local.dao.TaskDao;
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
public final class TaskRepositoryImpl_Factory implements Factory<TaskRepositoryImpl> {
  private final Provider<TaskDao> taskDaoProvider;

  public TaskRepositoryImpl_Factory(Provider<TaskDao> taskDaoProvider) {
    this.taskDaoProvider = taskDaoProvider;
  }

  @Override
  public TaskRepositoryImpl get() {
    return newInstance(taskDaoProvider.get());
  }

  public static TaskRepositoryImpl_Factory create(javax.inject.Provider<TaskDao> taskDaoProvider) {
    return new TaskRepositoryImpl_Factory(Providers.asDaggerProvider(taskDaoProvider));
  }

  public static TaskRepositoryImpl_Factory create(Provider<TaskDao> taskDaoProvider) {
    return new TaskRepositoryImpl_Factory(taskDaoProvider);
  }

  public static TaskRepositoryImpl newInstance(TaskDao taskDao) {
    return new TaskRepositoryImpl(taskDao);
  }
}
