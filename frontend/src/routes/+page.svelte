<script>
    import "./styles.css";
    import { isAuthenticated } from "../store";
    import auth from "../auth.service";

    
</script>

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
            <p>
                Suche nach einem Motorrad, welches für eine Vermietung zu Verfügung
                steht.
            </p>
            <a class="primary-btn" href="/rides/ridesOverview">Vermietung finden</a>
        </div>
    {#if !$isAuthenticated}
        <div class="card">
            <h2>Anmelden</h2>
            <p>
                Melde dich jetzt an und überzeuge dich von den Vorteilen von
                RentMyBike. Profitiere von deinem unbenutzten Motorrad.
            </p>
            <a class="secondary-btn" href="/signup">Registrieren</a>
            <a class="primary-btn" href="/login">Anmelden</a>
        </div>
    {/if}
</section>

<style>
</style>
