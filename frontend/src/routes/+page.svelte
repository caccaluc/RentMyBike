<script>
    import "./styles.css";
    import { isAuthenticated } from "../store";
    import auth from "../auth.service";

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
{#if $isAuthenticated}
<div class="hero">
    <div class="hero-content">
        <h1>Willkommen bei RentMyBike!</h1>
        <p>Flexibel mieten, sorgenfrei fahren.</p>
    </div>
    <img src="/images/Logo_RMB.png" alt="Logo" class="logo" />
</div>

<div class="hero">
    <img src="/images/view.png" alt="Motorrad auf Straße" class="hero-image" />
</div>
¨
<section class="cards">
    <div class="card">
      <h2>Motorrad suchen</h2>
      <p>Suche nach einem Motorrad, welches für eine Vermietung zu Verfügung steht.</p>
      <a class="primary-btn" href="/rides/ridesOverview">Vermietung finden</a>
    </div>
    <div class="card">
      <h2>Anmelden</h2>
      <p>Melde dich jetzt an und überzeuge dich von den Vorteilen von RentMyBike. Profitiere von deinem unbenutzten Motorrad.</p>
      <a class="secondary-btn" href="/user/create">Registrieren</a>
      <button class="primary-btn">Anmelden</button>
    </div>
  </section>
  {:else}
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">Login</div>
                    <div class="card-body">
                        <form
                            on:submit|preventDefault={loginWithUsernameAndPassword}
                            bind:this={loginForm}
                            class="needs-validation"
                            novalidate
                        >
                            <div class="mb-3">
                                <label for="username" class="form-label"
                                    >Email</label
                                >
                                <input
                                    bind:value={username}
                                    type="text"
                                    class="form-control"
                                    id="username"
                                    name="username"
                                    required
                                />
                                <div class="invalid-feedback">
                                    Please provide your username.
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label"
                                    >Password</label
                                >
                                <input
                                    bind:value={password}
                                    type="password"
                                    class="form-control"
                                    id="password"
                                    name="password"
                                    required
                                />
                                <div class="invalid-feedback">
                                    Please provide your password.
                                </div>
                            </div>
                            <div class="row align-items-end">
                                <div class="col">
                                    <button
                                        type="submit"
                                        class="btn btn-primary">Log in</button
                                    >
                                </div>
                                <div class="col-auto">
                                    <a href="/signup">Sign up</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
{/if}


<style>
   
</style>