<?php
$token = "BOT_TOKEN";
if(isset($_GET['token']) && !empty($_GET['token'])){
	$token = $_GET['token'];
}
?>
<!DOCTYPE html>
<html>
	<head>
		<title>My First HTML Bot</title>
		<token><?php echo $token;?></token>
		<prefix>/</prefix>
	</head>
	<body>
		<commands>
			<command>
				<name>cookie</name>
				<script type="text/java">
					event.getChannel().sendMessage(":cookie:").queue();
				</script>
			</command>
			<command>
				<name>ping</name>
				<script type="text/java">
					event.getChannel().sendMessage("pong").queue();
				</script>
			</command>
		</commands>
	</body>
</html>