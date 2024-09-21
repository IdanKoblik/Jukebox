---
layout: home

hero:
  name: "Jukebox"
  text: "Minecraft nbs music player api"
  image:
    src: https://raw.githubusercontent.com/IdanKoblik/Jukebox/refs/heads/pages/img/logo.png
    alt: Logo
  actions:
    - theme: brand
      text: NBS files
      link: /nbsfiles
    - theme: alt
      text: Install
      link: /install

features:
  - title: Optimized Performance
    icon: 🔥
    details: Enhance your Minecraft experience with our efficient Jukebox library, designed for speed and reliability.
  - title: Multiple Version Support 
    icon: 💽
    details: Seamlessly integrate with different Minecraft versions, ensuring compatibility and flexibility for your projects.
  - title: Open Source
    icon: 📌
    details: Contribute and customize our library with an open-source framework, fostering a community of developers.
---
<script setup>
import {
  VPTeamPage,
  VPTeamPageTitle,
  VPTeamMembers
} from 'vitepress/theme';

const member = [
  {
    avatar: 'https://www.github.com/IdanKoblik.png',
    name: 'Idan Koblik',
    title: 'Maintainer',
    links: [
      { icon: 'github', link: 'https://github.com/IdanKoblik' },
      { icon: 'discord', link: 'https://discord.com/users/429212281914785793' },
      { icon: 'linkedin', link: 'https://www.linkedin.com/in/idan-k/' }
    ]
  },
]
</script>
<center>
    <VPTeamPageTitle>
        <template #title></template>
        <template #lead></template>
    </VPTeamPageTitle>
    <VPTeamPageSection>
          <VPTeamMembers
            size="medium" :members="member"
          />
    </VPTeamPageSection>
</center>
