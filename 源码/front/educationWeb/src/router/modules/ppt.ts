import { $t } from "@/plugins/i18n";
import { ppt } from "@/router/enums";
const IFrame = () => import("@/layout/frame.vue");

export default {
  path: "/ppt",
  redirect: "/ppt/index",
  meta: {
    icon: "ri:file-ppt-2-fill",
    title: "PPT",
    rank: ppt,
    roles: ["admin1", "teacher","admin1"]
  },
  children: [
    {
      path: "/index",
      name: "PPT",
      component: () => import("@/views/ppt/index.vue"),
      meta: {
        title: $t("menus.purePPT"),
        roles: ["admin1", "teacher","admin1"]
      }
    },
    {
      path: "/progress",
      name: "progress",
      component: () => import("@/views/ppt/progress.vue"),
      meta: {
        title: $t("menus.pptProgress"),
        showLink: false,
        roles: ["admin1", "teacher","admin1"]
      }
    },
    {
      path: "/ppt-history",
      name: "ppt-history",
      component: () => import("@/views/ppt/ppt-history.vue"),
      meta: {
        title: $t("menus.ppthistory"),
        showLink: true,
        roles: ["admin1", "teacher","admin1"]
      }
    },
  ]
} satisfies RouteConfigsTable;