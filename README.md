# Locked

**Locked** is a 2D side-scrolling platformer built with Java Swing.  
You wake up trapped in a dark dungeon. Somewhere ahead, a locked door blocks your only way out.  
Find the hidden key, survive the traps, and escape.

### Game mechanics

* **Exploration** — Move through the dungeon, jump over obstacles and gaps
* **Traps** — Avoid ground spikes and ceiling traps that kill instantly
* **Crates** — Search damaged crates to find the hidden key
* **Door** — Unlock the door at the end of the level once you have the key

### Goal

Find the **hidden key** stashed inside one of the damaged crates, make your way to the locked door at the far end of the dungeon, and unlock it to escape.

Every run is **randomly generated**: crate positions, trap placements, and the key location change each time.

**Tip:** Watch for the `[ E ]` prompt at the bottom of the screen, it tells you when you can interact with something!

---

## Controls

| Key | Action |
|-----|--------|
| `A` | Move left |
| `D` | Move right |
| `SPACE` | Jump |
| `E` | Interact — search crates, unlock door |

---

## How to run

***Requirements:*** Java 17 or newer

**Run via JAR** (easiest, no source code needed):

1. Download the repository from GitHub — **Code → Download ZIP** and extract, or use `git clone`
2. Open a terminal in the project folder (where `Locked.jar` is located)
3. Run:
```bash
java -jar Locked.jar
```
> Verify your Java version with: `java -version`

---

### IntelliJ IDEA

1. Download the repository from GitHub — **Code → Download ZIP** and extract, or use `git clone`
2. Open IDEA and choose **File → Open**, select the project folder
3. Open `src/Main.java`
4. Click the green triangle ▶ next to the `main` method and choose **Run 'Main.main()'**

---

## Key tips

* The key is always hidden in a **damaged crate** — look for the worn-out texture
* **Not every damaged crate** contains the key — keep searching if you get "Nothing here..."
* Watch the **HUD in the top-left corner** — it shows whether you have the key or not
