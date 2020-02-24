FROM oracle/graalvm-ce:19.2.0.1 as graalvm
COPY . /home/app/insta-bot
WORKDIR /home/app/insta-bot
RUN gu install native-image
RUN native-image --no-server -cp build/libs/insta-bot-*-all.jar --no-fallback -H:Class=nl.blackstardlb.insta.bot.Application --initialize-at-run-time=java.sql.DriverManager --report-unsupported-elements-at-runtime

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/insta-bot .
ENTRYPOINT ["./insta-bot"]
