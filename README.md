# [The White House Protectors](https://github.com/egorbostrov/2D_Tower_Defense)

## Team & Roller
### **Prosjektleder:** Egor Bostrov
### **Systemarkitekt:** Trym Fonn Jakobsen
### **Design og UX:** Kevinas Pliuskus
### **Testansvarlig:** William Manley
### **Support:** Tarjei Fløtre


## Om spillet
#### I en apokalyptisk verden i 2016 har et nytt virus blitt sluppet løs, hvor de smittede forvandles til zombier.<br>
#### Som general i militæret er det din oppgave å beskytte stien til Det hvite hus.<br>
#### Du må strategisk plassere ulike typer forsvarere for å hindre at zombiene når frem. <br>
#### Du har kun seks ekstra vakter til rådighet; disse vaktene fungerer som dine liv.<br>
#### Vaktene er svært svake og vil mest sannsynlig dø om de angripes. <br>
#### Hvis alle vaktene dør, vil zombiene kunne trenge gjennom og drepe vår elskede president, Barack Obama. <br>
#### Hele USA er på dine skuldre, lykke til general.


## Kjøring
### Kjøring av spillet:
#### Først kjøre `mvn clean package` i konsollen.
#### Deretter kjøre `java -jar target/white-house-defender-1.0-SNAPSHOT-fat.jar` i konsollen,
#### Hvis dette ikke fungere, bruk `mvn exec:java` i konsollen.
#### Kjører Java 17 eller senere.

### Kjøring av tester:
#### Kjøre `mvn clean test` i konsollen
#### Deretter følg denne stien [target/site/jacoco/index.html](target/site/jacoco/index.html) og åpne den i din browser, for å se resultatene.


## Kjente feil

#### Enemies off center of path
- Når farten på enemies blir for høy, går de litt og litt av stien.
- Dette gjør at spillet ikke ser så bra ut.

#### Fullscreen
- Når spillet blir satt i fullscreen, blir spillet ødelagt.
- Inputs blir feil og skaleringen, derfor er det ikke mulig å spille i fullscreen.
- Denne funkjsonen har derfor blitt deaktivert.


## Credits
- Alle Kildene som er brukt i spillet er "non-copyright" og kan brukes fritt av andre.
- Alle kan bli funnet i [resources](src/main/resources) mappen i spillet.
- [gunnershot.ogg](https://pixabay.com/sound-effects/hit-someting-6037/) - "hit something" by Pixabay
- [snipershot.ogg](https://pixabay.com/sound-effects/gun-shot-1-176892/) - "Gun Shot 1" by Audiosto
- [bombershot.ogg](https://pixabay.com/sound-effects/cannon-shot-14799/) - "Cannon shot" by Pixabay
- [bombexplosion.ogg](https://pixabay.com/sound-effects/large-underwater-explosion-190270/) - "Large Underwater Explosion" by DavidDumaisAudio
- [zombiedeathsound.ogg](https://pixabay.com/sound-effects/zombie-3-106344/) - "Zombie 3" by Pixabay
- [mainmenumusic.ogg](https://pixabay.com/music/synthwave-neon-gaming-128925/) - "Neon Gaming" by dopestuff
- [gamemusic.ogg](https://pixabay.com/music/upbeat-8-bit-legends-ancient-shrine-200457/) "8 Bit Legends - Ancient Shrine" by AntiPodeanWriter
- [GUNNER_DEFENDER](https://secrethideout.itch.io/team-wars-platformer-battle) - "Gunner" by Secret Hideout


## Copyright lisens
#### All grafikk og kode er laget av © 2024 `Obama Gaming`, bortsett fra GUNNER_DEFENER. 
#### Lyder er hentet av "non-copyright" nettsider og kan brukes fritt av andre, disse finnes i kilder.
#### Spillet er laget av `Obama Gaming` og kan brukes fritt av andre.