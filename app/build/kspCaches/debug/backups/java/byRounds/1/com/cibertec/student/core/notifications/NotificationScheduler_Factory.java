package com.cibertec.student.core.notifications;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class NotificationScheduler_Factory implements Factory<NotificationScheduler> {
  private final Provider<Context> contextProvider;

  public NotificationScheduler_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NotificationScheduler get() {
    return newInstance(contextProvider.get());
  }

  public static NotificationScheduler_Factory create(
      javax.inject.Provider<Context> contextProvider) {
    return new NotificationScheduler_Factory(Providers.asDaggerProvider(contextProvider));
  }

  public static NotificationScheduler_Factory create(Provider<Context> contextProvider) {
    return new NotificationScheduler_Factory(contextProvider);
  }

  public static NotificationScheduler newInstance(Context context) {
    return new NotificationScheduler(context);
  }
}
