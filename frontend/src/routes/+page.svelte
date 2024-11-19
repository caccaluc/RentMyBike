<script>
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
      <p>Suche nach einem Motorrad, welches für eine Vermietung zu Verfügung steht.</p>
      <button class="primary-btn">Motorrad suchen</button>
    </div>
    <div class="card">
      <h2>Anmelden</h2>
      <p>Melde dich jetzt an und überzeuge dich von den Vorteilen von RentMyBike. Profitiere von deinem unbenutzten Motorrad.</p>
      <button class="secondary-btn">Registrieren</button>
      <button class="primary-btn">Anmelden</button>
    </div>
  </section>
  

<style>
   
  .cards {
    display: flex;
    gap: 2rem;
    justify-content: center;
    margin: 3rem auto;
    max-width: 1200px;
  }

  .card {
    background-color: #fff;
    padding: 2rem;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    flex: 1;
    text-align: center;
  }

  .card h2 {
    font-size: 1.75rem;
    margin-bottom: 1rem;
  }

  .card p {
    font-size: 1rem;
    color: #666;
    margin-bottom: 1.5rem;
  }

  .primary-btn {
    background-color: #007bff;
    color: #fff;
    padding: 0.75rem 1.5rem;
    border: none;
    border-radius: 25px;
    cursor: pointer;
    font-size: 1rem;
    transition: all 0.3s ease;
  }

  .primary-btn:hover {
    background-color: #0056b3;
    box-shadow: 0px 8px 15px rgba(0, 91, 187, 0.3);
    transform: translateY(-3px);
  }

  .secondary-btn {
    background-color: #6c757d;
    color: #fff;
    padding: 0.75rem 2rem;
    border: none;
    border-radius: 25px;
    cursor: pointer;
    font-size: 1rem;
    margin-right: 0.5rem;
    transition: all 0.3s ease;
  }

  .secondary-btn:hover {
    background-color: #5a6268;
    box-shadow: 0px 8px 15px rgba(90, 98, 104, 0.3);
    transform: translateY(-3px);
  }
  .hero {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 2rem;
    background-color: #ffffff;
    border-radius: 10px;
    margin: 2rem auto;
    max-width: 1200px;
  }

  .hero-content {
    max-width: 600px;
  }

  .hero-content h1 {
    font-size: 3rem;
    font-weight: bold;
    margin: 0;
  }

  .hero-content p {
    font-size: 1.25rem;
    color: #070707;
  }

  .logo {
    height: 100px;
    margin-left: 2rem;
  }

    .hero-image {
        width: 100%;
        border-radius: 10px;
    }

</style>