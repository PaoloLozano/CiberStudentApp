package com.cibertec.student.presentation.notes;

import com.cibertec.student.domain.repository.AuthRepository;
import com.cibertec.student.domain.repository.NoteRepository;
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
public final class NotesViewModel_Factory implements Factory<NotesViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<NoteRepository> noteRepositoryProvider;

  public NotesViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<NoteRepository> noteRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.noteRepositoryProvider = noteRepositoryProvider;
  }

  @Override
  public NotesViewModel get() {
    return newInstance(authRepositoryProvider.get(), noteRepositoryProvider.get());
  }

  public static NotesViewModel_Factory create(
      javax.inject.Provider<AuthRepository> authRepositoryProvider,
      javax.inject.Provider<NoteRepository> noteRepositoryProvider) {
    return new NotesViewModel_Factory(Providers.asDaggerProvider(authRepositoryProvider), Providers.asDaggerProvider(noteRepositoryProvider));
  }

  public static NotesViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<NoteRepository> noteRepositoryProvider) {
    return new NotesViewModel_Factory(authRepositoryProvider, noteRepositoryProvider);
  }

  public static NotesViewModel newInstance(AuthRepository authRepository,
      NoteRepository noteRepository) {
    return new NotesViewModel(authRepository, noteRepository);
  }
}
