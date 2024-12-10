<script>
  import "./styles.css";
  import { isAuthenticated, user } from "../store";
  import auth from "../auth.service";
</script>

<nav class="navbar navbar-expand-lg bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="/">RentMyBike</a>
    <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navbarNav"
      aria-controls="navbarNav"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon" />
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        {#if $isAuthenticated}
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="/rides/ridesOverview"
              >Vermietung finden</a
            >
          </li>
        {/if}
        {#if $isAuthenticated}
          <li class="nav-item">
            <a class="nav-link" href="/rides/myRides">Meine Vermietungen</a>
          </li>
        {/if}
        <li class="nav-item">
          <a class="nav-link" href="/Info">Über uns</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/user/all">Benutzerverwaltung</a>
        </li>
      </ul>
      <div class="d-flex">
        {#if $isAuthenticated}
          <span class="navbar-text me-2">{$user.name}</span>
          <button type="button" class="btn btn-primary" on:click={auth.logout}
            >Log Out</button>
        {/if}
      </div>
    </div>
  </div>
</nav>

<div class="container mt-3">
  <slot />
</div>
