const GEMINI_API_KEY = "AIzaSyC4S3mg6pPmPHj3wqNNe0Gd1gcVRgu0k0I"; // Replace with your key

function toggleChatbot() {
  const chatWindow = document.getElementById("chatWindow");

  if (chatWindow.style.display === "none" || chatWindow.style.display === "") {
    // When opening: clear previous messages and input
    document.getElementById("chatMessages").innerHTML = "";
    document.getElementById("userQuery").value = "";
    chatWindow.style.display = "block";
  } else {
    // When closing: simply hide
    chatWindow.style.display = "none";
  }
}


async function sendToGemini() {
  const userInputEl = document.getElementById("userQuery");
  const userInput = userInputEl.value.trim();
  if (!userInput) return;

  appendMessage("user", userInput);
  userInputEl.value = "Thinking..."; // Optional: temporary loading state

  try {
    const response = await fetch(
      "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + GEMINI_API_KEY,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          contents: [
            {
              parts: [{ text: userInput }]
            }
          ]
        })
      }
    );

    const data = await response.json();
    const botReply = data?.candidates?.[0]?.content?.parts?.[0]?.text ?? "🤖 AI: Sorry, I couldn't understand that.";
    appendMessage("ai", botReply);
  } catch (error) {
    console.error("Gemini API error:", error);
    appendMessage("ai", "🤖 AI: Something went wrong. Try again later.");
  }

  userInputEl.value = "";
}

function appendMessage(sender, text) {
  const chatBox = document.getElementById("chatMessages");
  const messageDiv = document.createElement("div");

  // Convert markdown-style bold **text** to real bold <strong>text</strong>
  const formattedText = text.replace(/\*\*(.*?)\*\*/g, "<strong>$1</strong>");

  messageDiv.innerHTML = (sender === "user" ? "👤 You: " : "🤖 AI: ") + formattedText;
  chatBox.appendChild(messageDiv);

  // Scroll to bottom smoothly
  chatBox.scrollTop = chatBox.scrollHeight;
}
