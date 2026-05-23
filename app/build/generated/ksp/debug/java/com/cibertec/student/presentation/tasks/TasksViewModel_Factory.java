package com.cibertec.student.presentation.tasks;

import com.cibertec.student.core.notifications.NotificationScheduler;
import com.cibertec.student.domain.repository.AuthRepository;
import com.cibertec.student.domain.repository.CourseRepository;
import com.cibertec.student.domain.repository.TaskRepository;
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
public final class TasksViewModel_Factory implements Factory<TasksViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<TaskRepository> taskRepositoryProvider;

  private final Provider<CourseRepository> courseRepositoryProvider;

  private final Provider<NotificationScheduler> notificationSchedulerProvider;

  public TasksViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<TaskRepository> taskRepositoryProvider,
      Provider<CourseRepository> courseRepositoryProvider,
      Provider<NotificationScheduler> notificationSchedulerProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.taskRepositoryProvider = taskRepositoryProvider;
    this.courseRepositoryProvider = courseRepositoryProvider;
    this.notificationSchedulerProvider = notificationSchedulerProvider;
  }

  @Override
  public TasksViewModel get() {
    return newInstance(authRepositoryProvider.get(), taskRepositoryProvider.get(), courseRepositoryProvider.get(), notificationSchedulerProvider.get());
  }

  public static TasksViewModel_Factory create(
      javax.inject.Provider<AuthRepository> authRepositoryProvider,
      javax.inject.Provider<TaskRepository> taskRepositoryProvider,
      javax.inject.Provider<CourseRepository> courseRepositoryProvider,
      javax.inject.Provider<NotificationScheduler> notificationSchedulerProvider) {
    return new TasksViewModel_Factory(Providers.asDaggerProvider(authRepositoryProvider), Providers.asDaggerProvider(taskRepositoryProvider), Providers.asDaggerProvider(courseRepositoryProvider), Providers.asDaggerProvider(notificationSchedulerProvider));
  }

  public static TasksViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<TaskRepository> taskRepositoryProvider,
      Provider<CourseRepository> courseRepositoryProvider,
      Provider<NotificationScheduler> notificationSchedulerProvider) {
    return new TasksViewModel_Factory(authRepositoryProvider, taskRepositoryProvider, courseRepositoryProvider, notificationSchedulerProvider);
  }

  public static TasksViewModel newInstance(AuthRepository authRepository,
      TaskRepository taskRepository, CourseRepository courseRepository,
      NotificationScheduler notificationScheduler) {
    return new TasksViewModel(authRepository, taskRepository, courseRepository, notificationScheduler);
  }
}
