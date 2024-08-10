import { $t } from "@/plugins/i18n";
import { psychological } from "@/router/enums";

export default {
  path: "/psychological",
  redirect: "/psychological/heartbeatBuddies",
  meta: {
    icon: "ri:ubuntu-fill",
    title: $t("menus.psychological"),
    rank: psychological,
    roles: ["student","admin1"]
  },
  children: [
    // {
    //   path: "/psychological/heartbeatBuddies",
    //   name: "heartbeatBuddies",
    //   component: () => import("@/views/psychological/heartbeatBuddies.vue"),
    //   meta: {
    //     title: $t("menus.heartbeatBuddies"),
    //     roles: ["student"]
    //   }
    // },
    {
      path: "/psychological/psychologicalQuestionnaire",
      name: "psychologicalQuestionnaire",
      component: () => import("@/views/psychological/psychologicalQuestionnaire.vue"),
      meta: {
        title: $t("menus.psychologicalQuestionnaire"),
        roles: ["student","admin1"]
      },
    },
    {
      path: "/psyresult",
      name: "psyresult",
      component: () => import("@/views/psychological/result.vue"),
      meta: {
        title: $t("psychological"),
        showLink: false,
        roles: ["student","admin1"]
      }
    },
    {
      path: "/heartDoctor",
      name: "heartDcotor",
      component: () => import("@/views/psychological/heartDoctor.vue"),
      meta: {
        title: $t("menus.heartDoctor"),
        roles: ["student","admin1"]
      }
    },
  ]
} satisfies RouteConfigsTable;