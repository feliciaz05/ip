# Melody Bot

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-17%2B-orange)
![License](https://img.shields.io/badge/License-MIT-green)

Melody is a friendly chatbot that helps you manage your tasks efficiently through a simple chat interface.

![Melody Screenshot](Ui.png)

## Features

- ✅ Add todos, deadlines, and events
- 📋 List all tasks
- ✔️ Mark tasks as done
- ❌ Delete tasks
- 🔍 Find tasks by keyword
- 💾 Automatic data persistence
- 🎨 Beautiful GUI with JavaFX

## Installation

1. Ensure you have Java 17 or later installed
2. Clone this repository:
   ```bash
   git clone https://github.com/your-username/melody-bot.git
   ```
3. Navigate to the project directory:
   ```bash
   cd melody-bot
   ```
4. Compile the project:
   ```bash
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -d out src/**/*.java
   ```
5. Run the application:
   ```bash
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp out melody.Launcher
   ```

## Usage

### Adding a todo
```
todo Read book
```

### Adding a deadline
```
deadline Submit report /by 2023-12-31
```

### Adding an event
```
event Team meeting /from 2023-11-15 2pm /to 2023-11-15 4pm
```

### Listing all tasks
```
list
```

### Marking a task as done
```
mark 1
```

### Deleting a task
```
delete 2
```

### Finding tasks
```
find book
```

### Getting help
```
help
```

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| Todo | `todo DESCRIPTION` | `todo Read book` |
| Deadline | `deadline DESCRIPTION /by DATE` | `deadline Submit report /by 2023-12-31` |
| Event | `event DESCRIPTION /from START /to END` | `event Meeting /from Mon 2pm /to Mon 4pm` |
| List | `list` | `list` |
| Mark | `mark INDEX` | `mark 1` |
| Delete | `delete INDEX` | `delete 2` |
| Find | `find KEYWORD` | `find book` |
| Help | `help` | `help` |

## File Structure

```
melody-bot/
├── src/
│   └── melody/
│       ├── command/
│       │   ├── AddCommand.java
│       │   ├── DeleteCommand.java
│       │   ├── ListCommand.java
│       │   └── ...
│       ├── exception/
│       │   └── MelodyException.java
│       ├── parser/
│       │   └── Parser.java
│       ├── storage/
│       │   └── Storage.java
│       ├── task/
│       │   ├── Task.java
│       │   ├── Todo.java
│       │   ├── Deadline.java
│       │   └── Event.java
│       ├── ui/
│       │   ├── DialogBox.java
│       │   ├── Launcher.java
│       │   ├── Main.java
│       │   └── MainWindow.java
│       └── Melody.java
├── docs/
│   └── README.md
├── data/
│   └── tasks.txt
└── images/
    ├── DaUser.png
    └── MelodyAI.png
```

## Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- Duke project team for inspiration
- JavaFX community for excellent documentation
- OpenJDK team for Java support