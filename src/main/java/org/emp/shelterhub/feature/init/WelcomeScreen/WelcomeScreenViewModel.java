package org.emp.shelterhub.feature.init.WelcomeScreen;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import org.emp.shelterhub.feature.MainContainer;
import org.emp.shelterhub.feature.navigation.MainNavigator;
import org.emp.shelterhub.lib.infrastructure.repository.UserRepository;

public class WelcomeScreenViewModel {

  private final BehaviorSubject<WelcomeScreenState> stateSubject =
      BehaviorSubject.createDefault(WelcomeScreenState.initialState());

  private final CompositeDisposable disposables = new CompositeDisposable();

  private StackPane root;

  public BehaviorSubject<WelcomeScreenState> getState() {
    return stateSubject;
  }

  public void setRoot(StackPane root) {
    this.root = root;
  }

  public void login(String username, String password) {
    stateSubject.onNext(stateSubject.getValue().withLoading(true).withErrorMessage(null));

    disposables.add(
        Single.fromCallable(() -> UserRepository.getInstance().authenticate(username, password))
            .subscribeOn(Schedulers.io())
            .subscribe(
                user ->
                    Platform.runLater(
                        () -> {
                          if (user != null) {
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withUser(user)
                                    .withLoading(false)
                                    .withErrorMessage(null));
                            navigateToMainScreen();
                          } else {
                            stateSubject.onNext(
                                stateSubject
                                    .getValue()
                                    .withUser(null)
                                    .withLoading(false)
                                    .withErrorMessage(
                                        "Nieprawidłowa nazwa użytkownika lub hasło."));
                          }
                        }),
                error ->
                    Platform.runLater(
                        () -> {
                          error.printStackTrace();
                          stateSubject.onNext(
                              stateSubject
                                  .getValue()
                                  .withLoading(false)
                                  .withErrorMessage("Błąd logowania: " + error.getMessage()));
                        })));
  }

  private void navigateToMainScreen() {
    if (root != null) {
      root.getChildren().clear();
      MainContainer mainContainer = new MainContainer(root);
      MainNavigator navigator = MainNavigator.getInstance();
      navigator.setMainContainer(mainContainer);
      navigator.navigateToMainContainer();
      dispose();
    }
  }

  public void dispose() {
    disposables.dispose();
  }
}
