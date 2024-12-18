<script>
  import { isAuthenticated } from "../../store";
  import auth from "../../auth.service";

  let email = "";
  let password = "";
  let firstName = "";
  let lastName = "";
  let signupForm;

  $: if ($isAuthenticated) {
    // reset the input values as soon as the user is registered.
    email = "";
    password = "";
  }

  function signup() {
    // form validation with bootstrap: see https://getbootstrap.com/docs/5.3/forms/validation/
    if (signupForm.checkValidity()) {
      console.log("signup");
      auth.signup(email, password, firstName, lastName);
    }
    signupForm.classList.add("was-validated");
  }
</script>

<h1>Registrieren</h1>
<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <div class="card">
        <div class="card-header">Registrieren</div>
        <div class="card-body text-start">
          <form
            on:submit|preventDefault={signup}
            bind:this={signupForm}
            class="needs-validation"
            novalidate
          >
            <div class="mb-3">
              <label for="username" class="form-label">E-Mail</label>
              <input
                bind:value={email}
                type="email"
                class="form-control"
                id="username"
                name="username"
                required
              />
              <div class="invalid-feedback">
                Bitte gib deine E-Mail Adresse an.
              </div>
            </div>
            <div class="mb-3">
              <label for="first-name" class="form-label">Vorname</label>
              <input
                bind:value={firstName}
                type="text"
                class="form-control"
                id="first-name"
                name="first-name"
              />
            </div>
            <div class="mb-3">
              <label for="last-name" class="form-label">Nachname</label>
              <input
                bind:value={lastName}
                type="text"
                class="form-control"
                id="last-name"
                name="last-name"
              />
            </div>
            <div class="mb-3">
              <label for="password" class="form-label">Passwort</label>
              <input
                bind:value={password}
                type="password"
                class="form-control"
                id="password"
                name="password"
                required
              />
              <div class="invalid-feedback">Wähle dein Passwort.</div>
            </div>
            <div class="row align-items-end">
              <div class="col">
                <button type="submit" class="btn btn-primary">Registrieren</button>
              </div>
              <div class="col-auto">
                <a href="/">Log in</a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
