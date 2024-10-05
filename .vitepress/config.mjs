import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: '/Jukebox',
  title: "Jukebox",
  description: "Minecraft Jukebox library",
  logo: 'https://raw.githubusercontent.com/IdanKoblik/Jukebox/refs/heads/pages/img/logo.png',
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Spigot', link: '/adventure' },
      {
              text: '2.0.0',
              items: [
                { text: 'Changelog', link: 'https://github.com/IdanKoblik/Jukebox/blob/master/CHANGELOG.md' },
                { text: 'License', link: 'https://github.com/IdanKoblik/Jukebox/blob/master/LICENSE' },
              ]
      }
    ],
    sidebar: {
      '/adventure/': [
        {
          text: 'Guide',
          collapsed: true,
          items: [
            { text: 'Install', link: '/adventure/' },
            { text: 'Instrument Manager', link: '/adventure/instrumentManager' },
            { text: 'Playing music', items: [
              { text: 'Song', link: '/adventure/song/song' },
              { text: 'Song queue', link: '/adventure/songQueue' },
            ]},
          ]
        }
      ]
    },
    socialLinks: [
      { icon: 'github', link: 'https://github.com/IdanKoblik/Jukebox/' },
      { icon: 'discord', link: 'https://discord.gg/pN4acjE5'}    
    ],
    footer: {
      message: "Released under the MIT License.",
      copyright: "Copyright © 2024-present Idan Koblik"
    },
    search: {
      provider: 'local'
    },
    lastUpdated: true,
  }
})
