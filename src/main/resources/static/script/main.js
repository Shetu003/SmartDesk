  function loadThought() {
    fetch("/thought/today")
      .then(res => res.text())
      .then(thought => {
        document.getElementById("thought").innerText = thought;
      });
  }

  function analyzeMoodAndRespond(mood) {
    const positiveMoods = ["happy", "joyful", "excited", "great", "fantastic", "cheerful"];
    const negativeMoods = ["sad", "tired", "angry", "anxious", "depressed", "upset"];

    const positiveResponses = [
      "You're doing amazing! Keep the momentum going! 🌟",
      "Great to hear that! Let's keep crushing it! 💪",
      "Your good vibes are inspiring! ✨",
      "Keep up the positive energy, you're on a roll! 🚀"
    ];

    const negativeResponses = [
      "Tough days build stronger people. You've got this. 💪",
      "Even storms pass—better times are ahead. ⏳",
      "Take a deep breath. You’re stronger than you think. 🎯",
      "Every day is a fresh start. Keep moving forward. 🌱"
    ];

    mood = mood.toLowerCase().trim();
    if (positiveMoods.includes(mood)) {
      return positiveResponses[Math.floor(Math.random() * positiveResponses.length)];
    } else if (negativeMoods.includes(mood)) {
      return negativeResponses[Math.floor(Math.random() * negativeResponses.length)];
    } else {
      return "Thanks for sharing! Stay strong and take care. 🌻";
    }
  }

  function submitMood() {
    const input = document.getElementById("moodInput");
    const mood = input.value.trim();
    const messagePara = document.getElementById("moodMessage");

    if (!mood) {
      messagePara.innerText = "Please enter your mood.";
      messagePara.style.color = "crimson";
      return;
    }

    const msg = analyzeMoodAndRespond(mood);
    messagePara.innerText = msg;
    messagePara.style.color = "#00695c";

    fetch(`/mood/submit?text=${encodeURIComponent(mood)}`)
      .then(res => res.text())
      .then(console.log);

    input.value = "";
  }

  function addTask() {
    const input = document.getElementById("taskInput");
    const desc = input.value.trim();
    if (!desc) return alert("Please enter a task.");
    fetch(`/task/add?description=${encodeURIComponent(desc)}`, { method: "POST" })
      .then(res => res.text())
      .then(alert);
    input.value = "";
    setTimeout(loadTasks, 500);
  }

  function loadTasks() {
    fetch("/task/all")
      .then(res => res.json())
      .then(tasks => {
        const list = document.getElementById("taskList");
        list.innerHTML = "";
        tasks.forEach(task => {
          const li = document.createElement("li");
          li.textContent = `${task.description} [${task.completed ? "Done" : "Pending"}]`;
          if (!task.completed) {
            const btn = document.createElement("button");
            btn.innerText = "Mark as Done";
            btn.onclick = () => markDone(task.id);
            li.appendChild(btn);
          }
          list.appendChild(li);
        });
      });
  }

  function markDone(id) {
    fetch(`/task/complete/${id}`, { method: "POST" })
      .then(res => res.text())
      .then(alert);
    setTimeout(loadTasks, 500);
  }

  function loadActivityFeed() {
    fetch("/activity/logs")
      .then(res => res.json())
      .then(logs => {
        const list = document.getElementById("activityList");
        list.innerHTML = "";
        logs.forEach(log => {
          const li = document.createElement("li");
          li.textContent = log;
          list.appendChild(li);
        });
      });
  }

  let timer;
  let totalSeconds = 25 * 60;

  function updateFocusDisplay() {
    const mins = String(Math.floor(totalSeconds / 60)).padStart(2, '0');
    const secs = String(totalSeconds % 60).padStart(2, '0');
    document.getElementById("focusTime").innerText = `${mins}:${secs}`;
  }

  function startFocus() {
    if (timer) return;
    timer = setInterval(() => {
      if (totalSeconds > 0) {
        totalSeconds--;
        updateFocusDisplay();
      } else {
        clearInterval(timer);
        timer = null;
        alert("✅ Focus session complete!");
        fetch("/focus/complete", { method: "POST" });
        resetFocus();
      }
    }, 1000);
  }

  function resetFocus() {
    clearInterval(timer);
    timer = null;
    totalSeconds = 25 * 60;
    updateFocusDisplay();
  }

  function showSection(section) {
    const sections = ['home', 'mood', 'tasks', 'about', 'contact'];

    // Toggle section visibility
    sections.forEach(id => {
      const div = document.getElementById(id + 'Section');
      if (div) div.style.display = (id === section) ? 'block' : 'none';
    });

    // Update active nav link
    sections.forEach(id => {
      const navItem = document.getElementById('nav-' + id);
      if (navItem) {
        navItem.classList.remove('active');
        if (id === section) {
          navItem.classList.add('active');
        }
      }
    });
  }



  function logout() {
    // Clear local/session storage (if any)
    localStorage.clear();

    // Clear frontend state
    document.getElementById("taskList").innerHTML = "";
    document.getElementById("moodMessage").innerText = "";
    document.getElementById("moodInput").value = "";
    document.getElementById("taskInput").value = "";
    document.getElementById("activityList").innerHTML = "";

    // Call backend to clear server-side activity logs
    fetch("/activity/clear", { method: "DELETE" })
      .then(() => {
        // Redirect after successful clear
        window.location.href = "index.html";
      })
      .catch(() => {
        // Redirect anyway even if error
        window.location.href = "index.html";
      });
  }




  window.onload = () => {
    showSection('home');
    loadThought();
    updateFocusDisplay();
    loadTasks();
    loadActivityFeed();
    setInterval(loadActivityFeed, 2000);
  };
