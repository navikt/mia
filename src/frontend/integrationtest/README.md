# Integrasjonstester
  
### Lokal kjøring
#### Oppsett
    Chromedrivere hentes fra et annet repo vha git submodule
    git submodule init
    git submodule update

#### Kjøring
    Du må ha en kjørende instans av mia
    npm run integrationtest

### Browserstack
#### Oppsett
    Man må ha en konto på browserstack.com som er koblet til organisasjonen.
    Invite kan man få fra adminbrukere @izisfro @pederpus
    Når man er logget inn så kan man finne brukernavn og key på automate.browserstack.com
    Miljøvariablene BROWSERSTACK_USER og BROWSERSTACK_KEY må settes til disse.

#### Kjøring
    Du må ha en kjørende instans av mia
    BROWSERSTACK_USER=user BROWSERSTACK_KEY=key npm run test:browser
     
