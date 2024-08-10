import { video } from "@/router/enums";
const IFrame = () => import("@/layout/frame.vue");
import { $t } from "@/plugins/i18n";

export default {
  path: "/video",
  redirect: "/video/index",
  meta: {
    icon: "ri:video-add-fill",
    title: $t("menus.video"),
    rank: video,
    roles: ["admin1", "teacher","admin1"]
  },
  children: [
    {
      path: "/video/index",
      name: "FramePpt",
      component: IFrame,
      meta: {
        title: $t("menus.video"),
        keepAlive: true,
        frameSrc: "http://111.229.20.120:5173/",
        frameLoading: false,
        roles: ["admin1", "teacher","admin1"]
      }
    }
  ]
} satisfies RouteConfigsTable;