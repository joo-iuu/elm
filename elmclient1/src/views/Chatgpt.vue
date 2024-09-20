<template>
  <div class="wrapper">
    <!-- header部分 -->
    <header>
      <p>智能推荐</p>
    </header>

    <div id="app">
      <div v-for="message in chatHistory" :key="message.id">
        <p v-if="message.role === 'user'">You: {{ message.content }}</p>
        <p v-if="message.role === 'bot'">Bot: {{ message.content }}</p>
      </div>
      <input type="text" v-model="messageInput" @keydown.enter="sendMessage">
      <button @click="sendMessage">Send</button>
    </div>

    <Footer></Footer>
  </div>
</template>

<script>
import axios from 'axios';
import Footer from '../components/Footer.vue';

export default {
  components: {
    Footer
  },
  data() {
    return {
      userId: 'user123',
      messageInput: '',
      chatHistory: []
    };
  },
  methods: {
    async sendMessage() {
      const requestData = {
        userId: this.userId,
        message: this.messageInput
      };

      try {
        const response = await axios.post('/chatgpt', requestData);
        const reply = response.data;

        this.chatHistory.push({
          role: 'user',
          content: this.messageInput
        });
        this.chatHistory.push({
          role: 'bot',
          content: reply
        });

        this.messageInput = '';
      } catch (error) {
        console.error(error);
      }
    }
  }
};
</script>

<style scoped>
/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
  overflow-x: hidden;
}

/****************** header部分 ******************/
.wrapper header {
  box-sizing: border-box;
  width: 100%;
  height: 12vw;
  background-color: #0097FF;
  color: #fff;
  font-size: 4.8vw;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 1000;
  display: flex;
  justify-content: center;
  align-items: center;
}

/****************** 聊天框 ******************/
#app {
  width: 100%;
  padding-top: 12vw;
  padding-bottom: 14vw;
}

#app p {
  margin: 3vw;
  padding: 0.5rem;
}

#app input[type="text"] {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: none;
  border-bottom: 1px solid #ccc;
}

#app button {
  margin-top: 0.5rem;
  padding: 0.5rem 1rem;
  font-size: 1rem;
  color: #fff;
  background-color: #0097FF;
  border: none;
  border-radius: 4px;
}
</style>