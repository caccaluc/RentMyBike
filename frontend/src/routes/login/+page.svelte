<script>
    import "../styles.css";
    import { isAuthenticated } from "../../store";
    import auth from "../../auth.service";
    import { jwt_token } from "../../store";
    import { onMount } from 'svelte';
    import { goto } from '$app/navigation';

    // Reaktives Statement, um Navigation zu triggern, wenn der Benutzer authentifiziert ist
    $: if ($isAuthenticated) {
        goto('/'); // Navigiert zur Homepage (Root-Pfad)
        }   

    let username = "";
    let password = "";
    let loginForm;

    function loginWithUsernameAndPassword() {
        // form validation with bootstrap: see https://getbootstrap.com/docs/5.3/forms/validation/
        if (loginForm.checkValidity()) {
            console.log("login");
            auth.login(username, password);
        }
        loginForm.classList.add("was-validated");
    }
</script>
    
    {#if !$isAuthenticated}
    
    <h1>Anmelden</h1>
    <div class="container mt-5">
        <div class="row justify-content-center"> 
          <div class="col-md-6">
            <div class="card">
              <div class="card-header">Anmelden</div>
              <div class="card-body text-start"> 
                <form
                  on:submit|preventDefault={loginWithUsernameAndPassword}
                  bind:this={loginForm}
                  class="needs-validation"
                  novalidate
                >
                  <!-- Email Eingabefeld -->
                  <div class="mb-3">
                    <label for="username" class="form-label">Email</label>
                    <input
                      bind:value={username}
                      type="text"
                      class="form-control"
                      id="username"
                      name="username"
                      required
                    />
                    <div class="invalid-feedback">
                      Bitte gib deine E-Mail Adresse an.
                    </div>
                  </div>
      
                  <!-- Passwort Eingabefeld -->
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
                    <div class="invalid-feedback">
                      Bitte gib dein Passwort an.
                    </div>
                  </div>
      
                  <!-- Anmelden Button und Registrieren Link -->
                  <div class="d-flex justify-content-between align-items-center">
                    <button type="submit" class="btn btn-primary">Anmelden</button>
                    <a href="/signup" class="text-decoration-none">Registrieren</a>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
      
    {/if}