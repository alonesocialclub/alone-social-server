package social.alone.server.push

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import social.alone.server.slack.SlackNotifier

@Component
class ScheduledPush(val slackNotifier: SlackNotifier) {

    @Scheduled(cron = "0 0 14 ? * * *", zone = "Asia/Seoul")
    fun pushTest() {
        slackNotifier.send("오후 2시 주기적으로 도는 푸쉬 크론")
    }
}