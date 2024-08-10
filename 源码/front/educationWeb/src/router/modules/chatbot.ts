import { $t } from "@/plugins/i18n";
import { chatbot,character } from "@/router/enums";

export default {
  path: "/chatbot",
  redirect: "/chatbot/index",
  meta: {
    icon: "ri:wechat-2-fill",
    title: $t("menus.pureChat"),
    rank: chatbot,
    roles: ["student","admin1"]
  },
  children: [
    {
      path: "/chatbot/index",
      name: "Chatbot",
      component: () => import("@/views/chatbot/index.vue"),
      meta: {
        title: $t("menus.chatbot"),
        roles: ["student","admin1"]
      }
    },
    {
      path: "/chatbot/character",
      name: "character",
      component: () => import("@/views/chatbot/character.vue"),
      meta: {
        title: $t("menus.pureChar"),
        roles: ["student","admin1"]
      }
    }
  ]
} satisfies RouteConfigsTable;