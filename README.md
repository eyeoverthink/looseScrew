# looseScrew (`EyeOverthink`)
Unified Music Channel, 60FPS Video Broadcast Deck, and Interactive Code / REA Reverse-Engineering Studio hosted by the EyeOverthink Pixel Avatar.

## Architecture
- **AvatarHostEngine**: Procedural pixel-art avatar with cyan neon rim-lighting, custom PNG override, Web Speech API synthesis, and audio-reactive mouth sync.
- **Three Unified Broadcast Channels**:
  - `[MUSIC]`: Multi-track procedural WebAudio synthesizer & 64-band FFT spectrum analyzer.
  - `[VIDEO]`: 60FPS procedural Canvas broadcast deck with Fraymus Portal horizon and tesseract projection.
  - `[CODE & REA_LAB]`: Interactive split-pane code sandbox and 8-step Reverse Engineer Anything (REA) verification workbench.
- **Fraymus / Lazarus Engine Backend**: `LooseScrewEngine.java` virtual-thread HTTP & Akashic Fossil telemetry server.

## Run Locally
1. Open `index.html` directly in any browser, OR
2. Run the Java 21 Fraymus/Lazarus backend:
   ```bash
   javac LooseScrewEngine.java && java LooseScrewEngine
   ```
